package com.namseox.st144_icon_changer.ui.setwallpaper

import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivitySetWallpaperBinding
import com.namseox.st144_icon_changer.dialog.DialogSetWallpaper
import com.namseox.st144_icon_changer.ui.success.SuccessActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.Const.TYPE
import com.namseox.st144_icon_changer.utils.Const.WALLPAPER
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick

class SetwallpaperActivity : AbsBaseActivity<ActivitySetWallpaperBinding>() {
    var path = ""
    override fun getLayoutId(): Int = R.layout.activity_set_wallpaper

    override fun initView() {
        path = intent.getStringExtra(DATA).toString()
        Glide.with(applicationContext).load(ASSET + path).into(binding.imv)
    }

    override fun initAction() {
        binding.apply {
            imvBack.onSingleClick { finish() }
            btnCreatNew.onSingleClick {
                var dialog = DialogSetWallpaper(this@SetwallpaperActivity,ASSET + path)
                dialog.onClick = {
                    startActivity(
                        newIntent(applicationContext, SuccessActivity::class.java).putExtra(
                            DATA,
                            ASSET + path
                        ).putExtra(TYPE, WALLPAPER)
                    )
                }
                dialog.show()
            }
        }
    }
}