package com.namseox.st144_icon_changer.ui.changeicon

import android.view.View
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityChangeIconsBinding
import com.namseox.st144_icon_changer.model.ChangeIconModel
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.success.SuccessActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.Const.TYPE
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon
import com.namseox.st144_icon_changer.utils.createMultipleShortcuts
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.showToast

class ChangeIconsActivity : AbsBaseActivity<ActivityChangeIconsBinding>() {
    var posChangeIcon = 0
    var pos = 0
    var arrChangeIcon = arrayListOf<ChangeIconModel>()
    lateinit var adapter: ChangeIconsAdapter
    lateinit var appAdapter: AppAdapter
    override fun getLayoutId(): Int = R.layout.activity_change_icons

    override fun initView() {
        if (arrApp.size == 0) {
            startActivity(newIntent(applicationContext, MainActivity::class.java))
        } else {
            pos = intent.getIntExtra("pos", 0)

            adapter = ChangeIconsAdapter()
            binding.rcv.adapter = adapter
            binding.rcv.itemAnimator = null
            arrIcon[pos].path.forEach {
                if (!it.contains("avatar")) {
                    arrChangeIcon.add(ChangeIconModel(it, false, null))
                }
            }
            adapter.submitList(arrChangeIcon)

            appAdapter = AppAdapter()
            binding.rcvChooseApp.adapter = appAdapter
            binding.rcvChooseApp.itemAnimator = null
            appAdapter.submitList(arrApp)
        }
    }

    override fun initAction() {
        binding.imvClose.onSingleClick {
            binding.llBottomSheet.visibility = View.GONE
        }
        binding.llBottomSheet.onSingleClick {
            binding.llBottomSheet.visibility = View.GONE
        }
        binding.ctl.onSingleClick { }
        binding.imvBack.onSingleClick { finish() }
        binding.tvApply.onSingleClick {
            if(arrChangeIcon.filter { it.check }.size>0){
                createMultipleShortcuts(
                    applicationContext,
                    arrChangeIcon.filter { it.check }.map { it.app!! },
                    arrChangeIcon.filter { it.check }.map { it.path }){
                    startActivity(
                        newIntent(
                            applicationContext,
                            SuccessActivity::class.java
                        ).putExtra(DATA, ASSET + arrIcon[pos].path.find { it.contains("avatar") })
                            .putExtra(TYPE, ICON)
                    )
                }
            }else{
             showToast(applicationContext,R.string.you_have_no_choice_yet)
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