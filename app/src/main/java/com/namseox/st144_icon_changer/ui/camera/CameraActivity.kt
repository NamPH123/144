package com.namseox.st144_icon_changer.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCameraBinding
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.flashManager
import com.namseox.st144_icon_changer.utils.hasBackCamera
import com.namseox.st144_icon_changer.utils.hasFrontCamera
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.showToast
import java.io.File
import java.io.FileOutputStream

class CameraActivity : AbsBaseActivity<ActivityCameraBinding>() {
    var checkFlash = false
    var checkBack = true
    private var camera: Camera? = null
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    override fun getLayoutId(): Int = R.layout.activity_camera

    override fun initView() {
//        previewView
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                !checkPermision(this)
            } else {
                false
            }
        ) {
            finish()
        } else {
            flashManager(false, camera)
            initCameraProvider()
        }
    }

    private fun initCameraProvider() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            if (checkBack) {
                if (hasBackCamera) {
                    startCamera(checkBack)
                } else {
                    showToast(applicationContext, R.string.rear_camera_not_supported)
                }
            }


        }, ContextCompat.getMainExecutor(this))
    }

    private fun startCamera(checkBack: Boolean) {
        preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.previewView.surfaceProvider)
        }
        val rotation = try {
            windowManager.defaultDisplay.rotation
        } catch (e: Exception) {
            0
        }

        imageCapture = ImageCapture.Builder().setTargetRotation(rotation).build()

        val cameraSelector = if (checkBack) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }
        try {
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture
            )
        } catch (exc: Exception) {

        }
    }

    override fun initAction() {
        binding.btnCamera.onSingleClick {
            val imagesDir = File(filesDir, "cache")
            if (!imagesDir.exists()) imagesDir.mkdirs()
            val photoFile = File(imagesDir, "cache.png")

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        // Ảnh đã lưu thành công, xoay đúng chiều nếu cần
                        fixImageOrientation(photoFile)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        showToast(applicationContext, R.string.photo_capture_failed)
                    }
                }
            )
        }
        binding.imvBack.onSingleClick { finish() }
        binding.imvRevert.onSingleClick {
            if (checkFlash && checkBack) {
                checkFlash = false
                flashManager(checkFlash, camera)
                binding.imvFlash.setImageResource(R.drawable.imv_flash_off)
                showToast(applicationContext, R.string.no_front_flash_support)
            }

            checkBack = !checkBack
            if (checkBack) {
                if (hasBackCamera) {
                    startCamera(checkBack)
                } else {
                    showToast(applicationContext, R.string.rear_camera_not_supported)
                }
            } else {
                if (hasFrontCamera) {
                    startCamera(checkBack)
                } else {
                    showToast(applicationContext, R.string.front_camera_not_supported)
                }
            }
        }
        binding.imvFlash.onSingleClick {
            if (!checkBack) {
                showToast(applicationContext, R.string.no_front_flash_support)
            } else {
                checkFlash = !checkFlash
                if (checkFlash) {
                    binding.imvFlash.setImageResource(R.drawable.ic_flash)
                } else {
                    binding.imvFlash.setImageResource(R.drawable.imv_flash_off)
                }
                if (!flashManager(checkFlash, camera)) {
                    showToast(applicationContext, R.string.phone_does_not_support_flash)
                }
            }
        }
    }
    fun fixImageOrientation(file: File) {
        try {
            val exif = ExifInterface(file.absolutePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val rotatedBitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
                else -> bitmap
            }

            // Ghi đè lại file đã xoay
            val out = FileOutputStream(file)
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun rotateBitmap(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}