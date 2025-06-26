package com.namseox.st144_icon_changer.ui.camera

import android.Manifest
import android.content.pm.PackageManager
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
import com.namseox.st144_icon_changer.ui.editphoto.EditPhotoActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.flashManager
import com.namseox.st144_icon_changer.utils.hasBackCamera
import com.namseox.st144_icon_changer.utils.hasFrontCamera
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.showToast
import java.io.File

class CameraActivity : AbsBaseActivity<ActivityCameraBinding>() {
    var checkFlash = false
    var checkBack = true
    private var camera: Camera? = null
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    override fun getLayoutId(): Int = R.layout.activity_camera

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
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
            binding.previewView.display.rotation
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
            var photoFile = File(imagesDir, "cache.png")

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(applicationContext),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        startActivity(newIntent(applicationContext, EditPhotoActivity::class.java).putExtra(DATA,photoFile.path))
                    }

                    override fun onError(exc: ImageCaptureException) {
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
                    checkFlash = false
                    binding.imvFlash.setImageResource(R.drawable.imv_flash_off)
                    showToast(applicationContext, R.string.phone_does_not_support_flash)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (checkFlash) {
            if (!flashManager(checkFlash, camera)) {
                checkFlash = false
                binding.imvFlash.setImageResource(R.drawable.imv_flash_off)
                showToast(applicationContext, R.string.phone_does_not_support_flash)
            }
        }
    }
}