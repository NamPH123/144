package com.namseox.st144_icon_changer.ui.creatnewwallpaper

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCreateNewWallpaperBinding
import com.namseox.st144_icon_changer.ui.category.CategoryActivity
import com.namseox.st144_icon_changer.ui.main.wallpaper.WallpaperAdapter
import com.namseox.st144_icon_changer.utils.DataHelper.arrBG
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick

class CreatNewWallpaperActivity : AbsBaseActivity<ActivityCreateNewWallpaperBinding>() {
    var arrString = arrayListOf<String>()
    lateinit var adapter: WallpaperAdapter
    override fun getLayoutId(): Int = R.layout.activity_create_new_wallpaper

    override fun initView() {
        adapter = WallpaperAdapter()
        binding.rcv.adapter = adapter
        binding.rcv.itemAnimator = null
        arrBG.forEach {
            arrString.add(it.path[0])
        }
        adapter.submitList(arrString)
    }

    override fun initAction() {
        adapter.onClick = {

        }
        binding.apply {
            imvBack.onSingleClick { finish() }
            imvCategory.onSingleClick {
                startActivity(newIntent(applicationContext, CategoryActivity::class.java))
            }
            imvFromGallery.onSingleClick { }
        }
    }
}