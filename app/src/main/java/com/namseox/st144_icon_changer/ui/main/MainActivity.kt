package com.namseox.st144_icon_changer.ui.main

import android.app.ComponentCaller
import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityMainBinding
import com.namseox.st144_icon_changer.ui.gallery.GalleryActivity
import com.namseox.st144_icon_changer.ui.main.icons.IconsFragment
import com.namseox.st144_icon_changer.ui.main.themes.ThemesFragment
import com.namseox.st144_icon_changer.ui.main.wallpaper.WallpaperFragment
import com.namseox.st144_icon_changer.ui.setting.SettingActivity
import com.namseox.st144_icon_changer.utils.Const.REQUEST_STORAGE_PERMISSION
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.getData
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import com.namseox.st144_icon_changer.utils.backPress
import com.namseox.st144_icon_changer.utils.changeFragment
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.requesPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.compareTo
import kotlin.inc

@AndroidEntryPoint
class MainActivity : AbsBaseActivity<ActivityMainBinding>() {
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        if(arrApp.size==0){
            getData()
        }
        navHostFragment = supportFragmentManager.findFragmentById(R.id.flContainer) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.iconsFragment -> binding.tvTitle.text = getString(R.string.app_name)
                R.id.wallpaperFragment -> binding.tvTitle.text = getString(R.string.wallpaper)
                R.id.themesFragment -> binding.tvTitle.text = getString(R.string.themes)
            }
        }
    }

    override fun initAction() {
        binding.imvSetting.onSingleClick {
            startActivity(
                newIntent(
                    applicationContext,
                    SettingActivity::class.java
                )
            )
        }
    }
    override fun onBackPressed() {
        backPress(SharedPreferenceUtils.getInstance(applicationContext))
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
                    startActivity(newIntent(this, GalleryActivity::class.java))
                }
            }
        } else {
            if (checkPermision(this)) {
                startActivity(newIntent(this, GalleryActivity::class.java))
            }
        }

    }
}