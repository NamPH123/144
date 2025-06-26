package com.namseox.st144_icon_changer.ui.customize

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCustomizeBinding
import com.namseox.st144_icon_changer.dialog.DialogSetWallpaper
import com.namseox.st144_icon_changer.model.ChangeIconModel
import com.namseox.st144_icon_changer.ui.changeicon.AppAdapter
import com.namseox.st144_icon_changer.ui.changeicon.ChangeIconsAdapter
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.success.SuccessActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.THEMES
import com.namseox.st144_icon_changer.utils.Const.TYPE
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon
import com.namseox.st144_icon_changer.utils.DataHelper.arrTheme
import com.namseox.st144_icon_changer.utils.createMultipleShortcuts
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.show
import com.namseox.st144_icon_changer.utils.showToast

class CustomizeActivity : AbsBaseActivity<ActivityCustomizeBinding>() {
    lateinit var mBitmap : Bitmap
    var pos = 0
    var posChangeIcon = 0
    var checkSetWallpaper = true
    lateinit var adapter: ChangeIconsAdapter
    lateinit var appAdapter: AppAdapter
    var arrChangeIcon = arrayListOf<ChangeIconModel>()
    override fun getLayoutId(): Int = R.layout.activity_customize

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            pos = intent.getIntExtra(DATA, 0)
            Glide.with(applicationContext)
                .asBitmap()
                .load(ASSET + convertPath(arrTheme[pos]))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        mBitmap = resource
                        binding.imv.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })

            adapter = ChangeIconsAdapter()
            binding.rcv.adapter = adapter
            binding.rcv.itemAnimator = null
            arrIcon.find {
                it.category == (arrTheme[pos].substringBeforeLast(".").split("_"))[2]
            }!!.path.forEach {
                if(!it.contains("avatar")){
                    arrChangeIcon.add(ChangeIconModel(it, false, null))
                }
            }


            appAdapter = AppAdapter()
            binding.rcvChooseApp.adapter = appAdapter
            binding.rcvChooseApp.itemAnimator = null
            appAdapter.submitList(arrApp)

            arrChangeIcon[0].app = arrApp[0]
            arrChangeIcon[1].app = arrApp[1]
            arrChangeIcon[2].app = arrApp[2]
            arrChangeIcon[3].app = arrApp[3]
            arrChangeIcon[0].check = true
            arrChangeIcon[1].check = true
            arrChangeIcon[2].check = true
            arrChangeIcon[3].check = true
            adapter.submitList(arrChangeIcon)
        }

    }

    override fun initAction() {
        val wallpaperManager = WallpaperManager.getInstance(this)
        binding.apply {
            tvApply.onSingleClick {
                if(checkSetWallpaper){
                    var dialog = DialogSetWallpaper(this@CustomizeActivity)
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
                        createMultipleShortcuts(
                            applicationContext,
                            arrChangeIcon.filter { it.check }.map { it.app!! },
                            arrChangeIcon.filter { it.check }.map { it.path }){
                            startActivity(
                                newIntent(
                                    applicationContext,
                                    SuccessActivity::class.java
                                ).putExtra(DATA,ASSET+ arrTheme[pos]).putExtra(
                                    TYPE,
                                    THEMES
                                )
                            )
                        }
                    }
                    dialog.show()
                }else{
                    if(arrChangeIcon.filter { it.check }.isNotEmpty()){
                        createMultipleShortcuts(
                            applicationContext,
                            arrChangeIcon.filter { it.check }.map { it.app!! },
                            arrChangeIcon.filter { it.check }.map { it.path }){
                            startActivity(
                                newIntent(
                                    applicationContext,
                                    SuccessActivity::class.java
                                ).putExtra(DATA,ASSET+ arrTheme[pos]).putExtra(
                                    TYPE,
                                    THEMES
                                )
                            )
                        }
                    }else{
                        showToast(applicationContext,R.string.you_have_no_choice_yet)
                    }

                }
            }
            imvClose.onSingleClick {
                binding.llBottomSheet.visibility = View.GONE
            }
            llBottomSheet.onSingleClick {
                binding.llBottomSheet.visibility = View.GONE
            }
            binding.imvBack.onSingleClick { finish() }
            ctl.onSingleClick { }
            sw.onSingleClick {
                checkSetWallpaper = !checkSetWallpaper
                if (checkSetWallpaper) {
                    sw.setImageResource(R.drawable.ic_swith_true_per)
                } else {
                    sw.setImageResource(R.drawable.ic_swith_false_per)
                }
            }
            tvBG.onSingleClick {
                ctlBG.show()
                ctlIcons.hide()
                tvBG.setBackgroundResource(R.drawable.bg_choose)
                tvIcons.setBackgroundResource(R.drawable.bg_card_border)
                tvBG.setTextColor(resources.getColor(R.color.white))
                tvIcons.setTextColor(resources.getColor(R.color.e09cd5))
            }
            tvIcons.onSingleClick {
                ctlBG.hide()
                ctlIcons.show()
                tvIcons.setBackgroundResource(R.drawable.bg_choose)
                tvBG.setBackgroundResource(R.drawable.bg_card_border)
                tvBG.setTextColor(resources.getColor(R.color.e09cd5))
                tvIcons.setTextColor(resources.getColor(R.color.white))
            }
        }
        adapter.onClick = { pos, type ->
            when (type) {
                "plus" -> {
                    binding.rcvChooseApp.scrollToPosition(0)
                    binding.llBottomSheet.visibility = View.VISIBLE
                    posChangeIcon = pos
                }

                "sw" -> {
                    if (arrChangeIcon[pos].app == null) {
                        showToast(
                            applicationContext,
                            R.string.you_have_not_selected_an_application_to_apply
                        )
                    } else {
                        arrChangeIcon[pos].check = !arrChangeIcon[pos].check
                    }
                    adapter.submitList(arrChangeIcon)
                }

                "delete" -> {
                    arrChangeIcon[pos].app = null
                    arrChangeIcon[pos].check = false
                    adapter.submitList(arrChangeIcon)
                }
            }
        }
        appAdapter.onClick = {
            arrChangeIcon[posChangeIcon].app = arrApp[it]
            arrChangeIcon[posChangeIcon].check = true
            binding.llBottomSheet.visibility = View.GONE
            adapter.submitList(arrChangeIcon)
        }
    }
    fun convertPath(input: String): String {
        val fileName = input.substringAfterLast("/").substringBeforeLast("_") // ví dụ: minimal_18
        val code = fileName.substringBefore("_") // ví dụ: minimal
        return "bg/$code/${fileName}.jpg"
    }
}