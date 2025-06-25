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

class DialogSetWallpaper(activity: Activity, var imageUrl: String) :
    BaseDialog<DialogSetWallpaperBinding>(activity, true) {
    var onClick: (() -> Unit)? = null
    var bitmap: Bitmap? = null
    override fun getContentView(): Int = R.layout.dialog_set_wallpaper

    override fun initView() {
        Glide.with(context.applicationContext)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        val wallpaperManager = WallpaperManager.getInstance(context)
        binding.apply {
            tvSetLockScreen.onSingleClick {
                if (bitmap == null) {
                    showToast(context, R.string.please_wait_for_data_loading)
                } else {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        false,
                        WallpaperManager.FLAG_LOCK
                    )
                    dismiss()
                    onClick?.invoke()
                }
            }
            tvSetHomeScreen.onSingleClick {
                if (bitmap == null) {
                    showToast(context, R.string.please_wait_for_data_loading)
                } else {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        false,
                        WallpaperManager.FLAG_SYSTEM
                    )
                    dismiss()
                    onClick?.invoke()
                }
            }
            tvSetLockHome.onSingleClick {
                if (bitmap == null) {
                    showToast(context, R.string.please_wait_for_data_loading)
                } else {
                    wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        false,
                        WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                    )
                    dismiss()
                    onClick?.invoke()
                }
            }
        }
    }

    override fun bindView() {

    }
}