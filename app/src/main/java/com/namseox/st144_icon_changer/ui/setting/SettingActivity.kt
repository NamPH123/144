package com.namseox.st144_icon_changer.ui.setting

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivitySettingBinding
import com.namseox.st144_icon_changer.model.SettingModel
import com.namseox.st144_icon_changer.ui.language.LanguageActivity
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.policy
import com.namseox.st144_icon_changer.utils.rateUs
import com.namseox.st144_icon_changer.utils.shareApp
import com.namseox.st144_icon_changer.utils.unItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingActivity : AbsBaseActivity<ActivitySettingBinding>() {
    lateinit var adapter: SettingAdapter
    var listSetting = arrayListOf(
        SettingModel(R.drawable.imv_language, R.string.language),
        SettingModel(R.drawable.imv_share, R.string.share),
        SettingModel(R.drawable.imv_rate, R.string.rate_us),
        SettingModel(R.drawable.imv_policy, R.string.policy),
    )

    @Inject
    lateinit var sharedPreferences: SharedPreferenceUtils
    var x = 0L
    override fun getLayoutId(): Int = R.layout.activity_setting
    override fun initView() {
        try {
            if (sharedPreferences.getBooleanValue("rate2")) {
                listSetting.removeAt(1)
            }
        }catch (e : Exception){
            listSetting.removeAt(1)
        }
        adapter = SettingAdapter()
        binding.rcv.adapter = adapter
        binding.rcv.itemAnimator = null
        binding.rcv.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        adapter.submitList(listSetting)
        unItem = {
            listSetting.removeAt(2)
            adapter.submitList(listSetting)
        }
        adapter.onClick = {
            when (it) {
                R.drawable.imv_language -> {
                    startActivity(newIntent(applicationContext, LanguageActivity::class.java))
                }

                R.drawable.imv_rate -> {
                    rateUs(0, binding.rcv)
                }

                R.drawable.imv_share -> {
                    if (System.currentTimeMillis() - x >= 1500) {
                        shareApp()
                        x = System.currentTimeMillis()
                    }
                }

                R.drawable.imv_policy -> {
                    policy()
                }
            }
        }
    }

    override fun initAction() {
        binding.imvBack.onSingleClick { finish() }
    }

    override fun onResume() {
        super.onResume()

    }
}