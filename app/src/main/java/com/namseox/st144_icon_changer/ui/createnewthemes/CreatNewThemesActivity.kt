package com.namseox.st144_icon_changer.ui.createnewthemes

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCreateNewThemeBinding
import com.namseox.st144_icon_changer.ui.customize.CustomizeActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.main.themes.ThemeAdapter
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrTheme
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick

class CreatNewThemesActivity : AbsBaseActivity<ActivityCreateNewThemeBinding>() {
    lateinit var adapterTheme: ThemeAdapter
    override fun getLayoutId(): Int = R.layout.activity_create_new_theme

    override fun initView() {
        adapterTheme = ThemeAdapter()
        binding.rcv.adapter = adapterTheme
        adapterTheme.submitList(arrTheme)
    }

    override fun initAction() {
        adapterTheme.onCLick = {
            startActivity(
                newIntent(applicationContext, CustomizeActivity::class.java).putExtra(
                    DATA,
                    it
                )
            )
        }
        binding.imvBack.onSingleClick {
            startActivity(
                newIntent(
                    applicationContext,
                    MainActivity::class.java
                )
            )
        }
    }
}