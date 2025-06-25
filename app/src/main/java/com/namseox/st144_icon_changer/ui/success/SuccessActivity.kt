package com.namseox.st144_icon_changer.ui.success

import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivitySuccessfullyBinding
import com.namseox.st144_icon_changer.ui.createnewthemes.CreatNewThemesActivity
import com.namseox.st144_icon_changer.ui.creatnewicons.CreatNewIconsActivity
import com.namseox.st144_icon_changer.ui.creatnewwallpaper.CreatNewWallpaperActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.Const.THEMES
import com.namseox.st144_icon_changer.utils.Const.TYPE
import com.namseox.st144_icon_changer.utils.Const.WALLPAPER
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.show

class SuccessActivity : AbsBaseActivity<ActivitySuccessfullyBinding>() {
    var path = ""
    var type = ""
    override fun getLayoutId(): Int = R.layout.activity_successfully

    override fun initView() {
        path = intent.getStringExtra(DATA).toString()
        type = intent.getStringExtra(TYPE).toString()
        when (type) {
            ICON -> {
                binding.cvBG.hide()
                binding.cvIcons.show()
                Glide.with(applicationContext).load(ASSET + path).into(binding.imvIcons)
                if(path.contains("avatar")){
                    binding.tv.text = getString(R.string.list_icon_has_been_changed_successfully)
                }else{
                    binding.tv.text = getString(R.string.icon_has_been_changed_successfully)
                }
            }
            THEMES -> {
                binding.tv.text = getString(R.string.theme_has_been_setup_successfully)
                binding.cvBG.show()
                binding.cvIcons.hide()
                Glide.with(applicationContext).load(ASSET + path).into(binding.imvBg)
            }
            WALLPAPER -> {
                binding.tv.text = getString(R.string.background_has_been_setup_successfully)
                binding.cvBG.show()
                binding.cvIcons.hide()
                Glide.with(applicationContext).load(ASSET + path).into(binding.imvBg)
            }
        }
    }

    override fun initAction() {
        binding.apply {
            imvBack.onSingleClick { finish() }
            imvHome.onSingleClick {
                startActivity(
                    newIntent(
                        applicationContext,
                        MainActivity::class.java
                    )
                )
            }
            btnCreatNew.onSingleClick {
                when (type) {
                    ICON -> {
                        startActivity(
                            newIntent(
                                applicationContext,
                                CreatNewIconsActivity::class.java
                            )
                        )
                    }

                    THEMES -> {
                        startActivity(
                            newIntent(
                                applicationContext,
                                CreatNewThemesActivity::class.java
                            )
                        )
                    }

                    WALLPAPER -> {
                        startActivity(
                            newIntent(
                                applicationContext,
                                CreatNewWallpaperActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}