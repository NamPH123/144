package com.namseox.st144_icon_changer.dialog

import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.BaseDialog
import com.namseox.st144_icon_changer.databinding.DialogSetWallpaperBinding
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.showToast

class DialogSetWallpaper(activity: Activity) :
    BaseDialog<DialogSetWallpaperBinding>(activity, true) {
    var onClick: ((Int) -> Unit)? = null
    override fun getContentView(): Int = R.layout.dialog_set_wallpaper

    override fun initView() {

        binding.apply {
            tvSetLockScreen.onSingleClick {
                    dismiss()
                    onClick?.invoke(0)

            }
            tvSetHomeScreen.onSingleClick {
                    dismiss()
                    onClick?.invoke(1)

            }
            tvSetLockHome.onSingleClick {
                    dismiss()
                    onClick?.invoke(2)

            }
        }
    }

    override fun bindView() {

    }
}