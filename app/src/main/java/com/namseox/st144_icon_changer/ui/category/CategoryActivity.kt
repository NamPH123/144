package com.namseox.st144_icon_changer.ui.category

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCategoryBinding
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.main.wallpaper.WallpaperAdapter
import com.namseox.st144_icon_changer.ui.setwallpaper.SetwallpaperActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrBG
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick

class CategoryActivity : AbsBaseActivity<ActivityCategoryBinding>() {
    lateinit var adapter: CategoryAdapter
    lateinit var adapterWallpaper: WallpaperAdapter
    override fun getLayoutId(): Int = R.layout.activity_category

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            adapter = CategoryAdapter()
            binding.rcvCategory.adapter = adapter
            binding.rcvCategory.itemAnimator = null
            adapter.submitList(arrBG)

            adapterWallpaper = WallpaperAdapter()
            binding.rcv.adapter = adapterWallpaper
            binding.rcv.itemAnimator = null
            adapterWallpaper.submitList(arrBG[0].path)
        }

    }

    override fun initAction() {
        adapter.onClick = {
            adapter.pos = it
            adapter.submitList(arrBG)
            adapterWallpaper.submitList(arrBG[it].path)
        }
        adapterWallpaper.onClick = {
            startActivity(
                newIntent(applicationContext, SetwallpaperActivity::class.java).putExtra(
                    DATA,
                   ASSET+ arrBG[adapter.pos].path[it]
                )
            )
        }
        binding.apply {
            imvBack.onSingleClick { finish() }
        }
    }
}