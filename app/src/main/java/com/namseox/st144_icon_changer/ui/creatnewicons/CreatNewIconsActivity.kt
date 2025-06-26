package com.namseox.st144_icon_changer.ui.creatnewicons

import android.view.View
import androidx.core.widget.addTextChangedListener
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCreateNewIconsBinding
import com.namseox.st144_icon_changer.model.AppInfoModel
import com.namseox.st144_icon_changer.ui.editicons.EditIconsActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.main.icons.AppAdapter
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.searchApps
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick

class CreatNewIconsActivity : AbsBaseActivity<ActivityCreateNewIconsBinding>() {
    lateinit var adapterIcon: AppAdapter
    var arrSubApp = arrayListOf<AppInfoModel>()
    override fun getLayoutId(): Int = R.layout.activity_create_new_icons

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            arrSubApp.addAll(arrApp)
            adapterIcon = AppAdapter()
            binding.rcvApp.adapter = adapterIcon
            binding.rcvApp.itemAnimator = null
            adapterIcon.submitList(arrSubApp)
        }


    }

    override fun initAction() {
        binding.apply {
            imvBack.onSingleClick {startActivity(newIntent(applicationContext, MainActivity::class.java)) }
            tvSearch.addTextChangedListener {
                arrSubApp.clear()
                if (it.isNullOrEmpty()) {
                    arrSubApp.addAll(arrApp)
                } else {
                    arrSubApp.addAll(searchApps(it.toString()))
                }
                if (arrSubApp.size > 0) {
                    imvNoItem.visibility = View.GONE
                    tvNoItem.visibility = View.GONE
                } else {
                    imvNoItem.visibility = View.VISIBLE
                    tvNoItem.visibility = View.VISIBLE
                }
                adapterIcon.submitList(arrSubApp)
            }
        }
        adapterIcon.onClick = {
            newIntent(applicationContext, EditIconsActivity::class.java).putExtra(DATA, arrApp.indexOf(it))
        }
    }
}