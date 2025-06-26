package com.namseox.st144_icon_changer.ui.setwallpaper

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivitySetWallpaperBinding
import com.namseox.st144_icon_changer.dialog.DialogSetWallpaper
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.success.SuccessActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.Const.TYPE
import com.namseox.st144_icon_changer.utils.Const.WALLPAPER
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.showToast

class SetwallpaperActivity : AbsBaseActivity<ActivitySetWallpaperBinding>() {
    var path = ""
    lateinit var mBitmap : Bitmap
    override fun getLayoutId(): Int = R.layout.activity_set_wallpaper

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            path = intent.getStringExtra(DATA).toString()
            Glide.with(applicationContext)
                .asBitmap()
                .load(path)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        mBitmap = resource
                        binding.imv.setImageBitmap(resource)
                        binding.llLoading.hide()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })
        }

    }

    override fun initAction() {
        val wallpaperManager = WallpaperManager.getInstance(this)
        binding.llLoading.onSingleClick {  }
        binding.apply {
            imvBack.onSingleClick { finish() }
            btnCreatNew.onSingleClick {
                var dialog = DialogSetWallpaper(this@SetwallpaperActivity)
                dialog.onClick = {
                    when(it){
                        0->{
                            wallpaperManager.setBitmap(
                                mBitmap,
                                null,
                                false,
                                WallpaperManager.FLAG_LOCK
                            )
                        }
                        1->{
                            wallpaperManager.setBitmap(
                                mBitmap,
                                null,
                                false,
                                WallpaperManager.FLAG_SYSTEM
                            )
                        }
                        2->{
                            wallpaperManager.setBitmap(
                                mBitmap,
                                null,
                                false,
                                WallpaperManager.FLAG_LOCK
                            )
                            binding.imv.postDelayed({
                                wallpaperManager.setBitmap(
                                    mBitmap,
                                    null,
                                    false,
                                    WallpaperManager.FLAG_SYSTEM
                                )
                            },500)
                        }
                    }
                    showToast(applicationContext,R.string.wallpaper_set_successfully)
                    startActivity(
                        newIntent(applicationContext, SuccessActivity::class.java).putExtra(
                            DATA,
                            path
                        ).putExtra(TYPE, WALLPAPER)
                    )
                }
                dialog.show()
            }
        }
    }
}