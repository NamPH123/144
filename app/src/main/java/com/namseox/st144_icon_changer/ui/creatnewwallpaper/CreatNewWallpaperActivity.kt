package com.namseox.st144_icon_changer.ui.creatnewwallpaper

import androidx.core.app.ActivityCompat
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCreateNewWallpaperBinding
import com.namseox.st144_icon_changer.ui.category.CategoryActivity
import com.namseox.st144_icon_changer.ui.gallery.GalleryActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.main.wallpaper.WallpaperAdapter
import com.namseox.st144_icon_changer.ui.setwallpaper.SetwallpaperActivity
import com.namseox.st144_icon_changer.utils.Const
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.REQUEST_STORAGE_PERMISSION
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrBG
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.checkUsePermision
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.newIntentOnCreat
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.requesPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatNewWallpaperActivity : AbsBaseActivity<ActivityCreateNewWallpaperBinding>() {
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    var arrString = arrayListOf<String>()
    lateinit var adapter: WallpaperAdapter
    override fun getLayoutId(): Int = R.layout.activity_create_new_wallpaper

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            adapter = WallpaperAdapter()
            binding.rcv.adapter = adapter
            binding.rcv.itemAnimator = null
            arrBG.forEach {
                arrString.add(it.path[0])
            }
            adapter.submitList(arrString)
        }

    }

    override fun initAction() {
        adapter.onClick = {
            startActivity(newIntent(applicationContext, SetwallpaperActivity::class.java).putExtra(
                DATA,
                ASSET+ arrString[it]
            ))
        }
        binding.apply {
            imvBack.onSingleClick { finish() }
            imvCategory.onSingleClick {
                startActivity(newIntentOnCreat(applicationContext, CategoryActivity::class.java))
            }
            imvFromGallery.onSingleClick {
                if (!checkPermision(this@CreatNewWallpaperActivity)) {
                    ActivityCompat.requestPermissions(
                        this@CreatNewWallpaperActivity,
                        checkUsePermision(),
                        Const.REQUEST_STORAGE_PERMISSION
                    )
                } else {
                    startActivity(newIntentOnCreat(applicationContext, GalleryActivity::class.java))
                }
            }
        }
    }

    var count = 0
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        count++
        if (count > 1) {
            sharedPreferenceUtils.putNumber("count", count)
            when (requesPermission(requestCode, permissions, grantResults)) {
                REQUEST_STORAGE_PERMISSION -> {
                    startActivity(newIntent(applicationContext, GalleryActivity::class.java))
                }

            }
        } else {
            if (requestCode == REQUEST_STORAGE_PERMISSION) {
                startActivity(newIntent(applicationContext, GalleryActivity::class.java))
            }
        }
    }
}