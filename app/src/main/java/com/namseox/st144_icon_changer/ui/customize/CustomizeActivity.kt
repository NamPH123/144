package com.namseox.st144_icon_changer.ui.customize

import android.view.View
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCustomizeBinding
import com.namseox.st144_icon_changer.dialog.DialogSetWallpaper
import com.namseox.st144_icon_changer.model.ChangeIconModel
import com.namseox.st144_icon_changer.ui.changeicon.AppAdapter
import com.namseox.st144_icon_changer.ui.changeicon.ChangeIconsAdapter
import com.namseox.st144_icon_changer.ui.success.SuccessActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
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
    var pos = 0
    var posChangeIcon = 0
    var checkSetWallpaper = true
    lateinit var adapter: ChangeIconsAdapter
    lateinit var appAdapter: AppAdapter
    var arrChangeIcon = arrayListOf<ChangeIconModel>()
    override fun getLayoutId(): Int = R.layout.activity_customize

    override fun initView() {
        pos = intent.getIntExtra(DATA, 0)
        Glide.with(applicationContext).load(ASSET + arrTheme[pos].substringBeforeLast("_")).into(binding.imv)

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
        adapter.submitList(arrChangeIcon)

        appAdapter = AppAdapter()
        binding.rcvChooseApp.adapter = appAdapter
        binding.rcvChooseApp.itemAnimator = null
        appAdapter.submitList(arrApp)
    }

    override fun initAction() {
        binding.apply {
            tvApply.onSingleClick {
                var dialog = DialogSetWallpaper(this@CustomizeActivity,ASSET + arrTheme[pos].substringBeforeLast("_"))
                dialog.onClick = {
                    createMultipleShortcuts(
                        applicationContext,
                        arrChangeIcon.filter { it.check }.map { it.app!! },
                        arrChangeIcon.filter { it.check }.map { it.path }){
                        startActivity(
                            newIntent(
                                applicationContext,
                                SuccessActivity::class.java
                            ).putExtra(DATA, arrTheme[pos]).putExtra(
                                TYPE,
                                THEMES
                            )
                        )
                    }
                }
                dialog.show()
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
            }
            tvIcons.onSingleClick {
                ctlBG.hide()
                ctlIcons.show()
                tvIcons.setBackgroundResource(R.drawable.bg_choose)
                tvBG.setBackgroundResource(R.drawable.bg_card_border)
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
            binding.llBottomSheet.visibility = View.GONE
            adapter.submitList(arrChangeIcon)
        }
    }
}