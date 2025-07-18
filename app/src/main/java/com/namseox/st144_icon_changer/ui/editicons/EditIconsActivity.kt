package com.namseox.st144_icon_changer.ui.editicons

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityEditIconsBinding
import com.namseox.st144_icon_changer.ui.camera.CameraActivity
import com.namseox.st144_icon_changer.ui.gallery.GalleryActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.success.SuccessActivity
import com.namseox.st144_icon_changer.utils.Const
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.Const.MYICON
import com.namseox.st144_icon_changer.utils.Const.REQUEST_CODE_CAMERA
import com.namseox.st144_icon_changer.utils.Const.REQUEST_STORAGE_PERMISSION
import com.namseox.st144_icon_changer.utils.Const.TYPE
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.checkUsePermision
import com.namseox.st144_icon_changer.utils.createMultipleShortcuts
import com.namseox.st144_icon_changer.utils.drawableToBitmap
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.hideKeyboard
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.requesPermission
import com.namseox.st144_icon_changer.utils.show
import com.namseox.st144_icon_changer.utils.showKeyboard
import com.namseox.st144_icon_changer.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class EditIconsActivity : AbsBaseActivity<ActivityEditIconsBinding>() {
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    var pos = 0
    var arrPath = arrayListOf<String>()
    var arrCategory = arrayListOf<String>()
    lateinit var adapterMyIcons: MyIconsAdapter
    lateinit var adapterCategory: CategoryAdapter
    lateinit var adapterPathCategory: PathCategoryAdapter
    var posChoose = arrayOf(-1, -1)
    var arrItemCategory = arrayListOf<String>()
    override fun getLayoutId(): Int = R.layout.activity_edit_icons

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            pos = intent.getIntExtra(DATA, 0)
            binding.imvIcons.setImageDrawable(arrApp[pos].icon)
//        Glide.with(applicationContext).load(ASSET + arrIcon[0].path[0]).into(binding.imvIcons)
            binding.tvEdit.text = arrApp[pos].name
            binding.edtEdit.setText(arrApp[pos].name)

            adapterMyIcons = MyIconsAdapter()
            binding.rcvMyIcons.adapter = adapterMyIcons
            binding.rcvMyIcons.itemAnimator = null

            getData()
            binding.ctlCategory.show()
            binding.ctlGallery.hide()
            adapterCategory = CategoryAdapter()
            binding.rcvCategory.adapter = adapterCategory
            binding.rcvCategory.itemAnimator = null
            arrCategory.addAll(arrIcon.map { it.category })
            adapterCategory.submitList(arrCategory)

            adapterPathCategory = PathCategoryAdapter()
            binding.rcvContentCategory.adapter = adapterPathCategory
            binding.rcvContentCategory.itemAnimator = null
            arrItemCategory.addAll(arrIcon[0].path)
            arrItemCategory.removeAt(arrItemCategory.size - 1)
            adapterPathCategory.submitList(arrItemCategory)
        }

    }

    override fun initAction() {
        binding.apply {
            imvBack.onSingleClick { finish() }
            imvEdit.onSingleClick {
                tvEdit.hide()
                imvEdit.hide()
                edtEdit.show()
                imvTick.show()
                edtEdit.postDelayed({
                    showKeyboard(edtEdit)

                }, 100)
                edtEdit.requestFocus()
                edtEdit.setSelection(edtEdit.text.toString().length)
            }
            imvTick.onSingleClick {
                if (edtEdit.text.toString().trim().isEmpty()) {
                    edtEdit.postDelayed({
                        showKeyboard(edtEdit)

                    }, 100)
                    edtEdit.requestFocus()
                    edtEdit.setSelection(0)
                    showToast(applicationContext, R.string.app_name_cannot_be_blank)
                } else {
                    hideKeyboard(edtEdit)
                    tvEdit.show()
                    imvEdit.show()
                    edtEdit.hide()
                    imvTick.hide()
                    tvEdit.setText(edtEdit.text.toString().trim())
                }
            }
            edtEdit.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (edtEdit.text.toString().trim().isEmpty()) {
                        edtEdit.postDelayed({
                            showKeyboard(edtEdit)

                        }, 100)
                        edtEdit.requestFocus()
                        edtEdit.setSelection(0)
                        showToast(applicationContext, R.string.app_name_cannot_be_blank)
                    } else {
                        hideKeyboard(edtEdit)
                        tvEdit.show()
                        imvEdit.show()
                        edtEdit.hide()
                        imvTick.hide()
                        tvEdit.setText(edtEdit.text.toString().trim())
                    }
                    true
                }
                false
            }
            llCategory.onSingleClick {
                ctlCategory.show()
                ctlGallery.hide()
                tvCategory.setTextColor("#2F2F2F".toColorInt())
                tvGallery.setTextColor("#E09CD5".toColorInt())
                ll1.setBackgroundColor("#E09CD5".toColorInt())
                ll2.setBackgroundColor("#ffffff".toColorInt())
            }
            llGallery.onSingleClick {
                ctlCategory.hide()
                ctlGallery.show()
                tvGallery.setTextColor("#2F2F2F".toColorInt())
                tvCategory.setTextColor("#E09CD5".toColorInt())
                ll1.setBackgroundColor("#ffffff".toColorInt())
                ll2.setBackgroundColor("#E09CD5".toColorInt())
            }
            tvApply.onSingleClick {
                createMultipleShortcuts(
                    applicationContext,
                    listOf(arrApp[pos]),
                    if(adapterMyIcons.pos>=0){
                        listOf(arrPath[adapterMyIcons.pos])
                    }else{
                        if(posChoose[0]>-1 && posChoose[1]>-1){
                            listOf(arrIcon[posChoose[0]].path[posChoose[1]])
                        }else{
                            listOf()
                        }
                    },
                    binding.tvEdit.text.toString().trim(),
                    if(posChoose[0]>-1 && posChoose[1]>-1 || adapterMyIcons.pos>=0){
                        null
                    }else{
                        drawableToBitmap(arrApp[pos].icon)
                    }
                ) {
                    startActivity(
                        newIntent(applicationContext, SuccessActivity::class.java).putExtra(
                            DATA,
                            if(adapterMyIcons.pos>=0){
                                arrPath[adapterMyIcons.pos]
                            }else{
                                if(posChoose[0]>-1 && posChoose[1]>-1){
                                    ASSET + arrIcon[posChoose[0]].path[posChoose[1]]
                                }else{
                                    pos
                                }
                            }
                        ).putExtra(TYPE, ICON)
                    )
                }


            }
            btnGallery.onSingleClick {
                if (!checkPermision(applicationContext)) {
                    ActivityCompat.requestPermissions(
                        this@EditIconsActivity,
                        checkUsePermision(),
                        Const.REQUEST_STORAGE_PERMISSION
                    )
                }else{
                    startActivity(newIntent(applicationContext, GalleryActivity::class.java).putExtra(DATA,ICON))
                }

            }
            btnCamera.onSingleClick {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity(newIntent(applicationContext, CameraActivity::class.java))
                } else {
                    ActivityCompat.requestPermissions(
                        this@EditIconsActivity,
                        arrayOf(Manifest.permission.CAMERA),
                        Const.REQUEST_CODE_CAMERA
                    )
                }
            }
        }
        adapterMyIcons.onClick = {
                posChoose = arrayOf(-1, -1)
                adapterMyIcons.pos = it
                adapterMyIcons.submitList(arrPath)
                adapterPathCategory.pos = -1
                adapterPathCategory.submitList(arrItemCategory)
            Glide.with(applicationContext).load(arrPath[it])
                .into(binding.imvIcons)
        }

        adapterCategory.onClick = {
                adapterCategory.pos = it
                adapterCategory.submitList(arrCategory)
                arrItemCategory.clear()
                arrItemCategory.addAll(arrIcon[it].path)
                arrItemCategory.removeAt(arrItemCategory.size - 1)
                if (adapterCategory.pos == posChoose[0]) {
                    adapterPathCategory.pos = posChoose[1]
                } else {
                    adapterPathCategory.pos = -1
                }
                adapterPathCategory.submitList(arrItemCategory)
        }

        adapterPathCategory.onClick = {
                posChoose = arrayOf(adapterCategory.pos, it)
                adapterPathCategory.pos = it
                adapterPathCategory.submitList(arrItemCategory)

                adapterMyIcons.pos = -1
                adapterMyIcons.submitList(arrPath)
                Glide.with(applicationContext).load(ASSET + arrItemCategory[it])
                    .into(binding.imvIcons)
        }


    }

    fun getData() {
        arrPath.clear()
        if (File(filesDir, MYICON).exists()) {
            File(filesDir, MYICON).listFiles()?.sortedByDescending { it.name }?.reversed()
                ?.forEach {
                    arrPath.add(it.path)
                }
            arrPath.reverse()
        }
        if (arrPath.size == 0) {
            binding.tvMyIcons.show()
        } else {
            binding.tvMyIcons.hide()
        }
        adapterMyIcons.submitList(arrPath)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG", "onRestart_____:000000 ")
        getData()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requesPermission(requestCode, permissions, grantResults)) {
            REQUEST_CODE_CAMERA -> {
                    startActivity(newIntent(applicationContext, CameraActivity::class.java))
            }
            REQUEST_STORAGE_PERMISSION ->{
                startActivity(newIntent(applicationContext, GalleryActivity::class.java).putExtra(DATA,ICON))
            }
        }
    }

}