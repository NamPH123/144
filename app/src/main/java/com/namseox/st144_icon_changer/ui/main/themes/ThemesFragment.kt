package com.namseox.st144_icon_changer.ui.main.themes

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseFragment
import com.namseox.st144_icon_changer.databinding.FragmentThemesBinding
import com.namseox.st144_icon_changer.ui.customize.CustomizeActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrTheme
import com.namseox.st144_icon_changer.utils.DataHelper.mutableLiveData
import com.namseox.st144_icon_changer.utils.newIntent

class ThemesFragment : AbsBaseFragment<FragmentThemesBinding, MainActivity>() {
    lateinit var adapterTheme: ThemeAdapter
    override fun getLayout(): Int = R.layout.fragment_themes

    override fun initView() {
        adapterTheme = ThemeAdapter()
        binding.rcv.adapter = adapterTheme

        mutableLiveData.observe(this){
            if(it==1){
                adapterTheme.submitList(arrTheme)
            }
        }
    }

    override fun setClick() {
        adapterTheme.onCLick = {
            startActivity(
                newIntent(requireContext(), CustomizeActivity::class.java).putExtra(
                    DATA,
                    it
                )
            )
        }
    }
}