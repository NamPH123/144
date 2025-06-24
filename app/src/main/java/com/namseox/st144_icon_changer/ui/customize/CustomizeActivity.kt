package com.namseox.st144_icon_changer.ui.customize

import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityCustomizeBinding
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrTheme

class CustomizeActivity : AbsBaseActivity<ActivityCustomizeBinding>() {
    var pos = 0
    override fun getLayoutId(): Int = R.layout.activity_customize

    override fun initView() {
        pos = intent.getIntExtra(DATA, 0)
        Glide.with(applicationContext).load(ASSET + arrTheme[pos].substringBeforeLast("_")).into(binding.imv)
    }

    override fun initAction() {

    }
}