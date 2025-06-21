package com.namseox.st144_icon_changer.ui.main.icons

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseFragment
import com.namseox.st144_icon_changer.databinding.FragmentIconsBinding
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon

class IconsFragment : AbsBaseFragment<FragmentIconsBinding, MainActivity>() {
    lateinit var adapterIcon: AppAdapter
    lateinit var adapterIconsChanger: IconsAdapter
    var arrIconChanger = arrayListOf<String>()
    override fun getLayout(): Int = R.layout.fragment_icons

    override fun initView() {
        adapterIcon = AppAdapter()
        binding.rcvApp.adapter = adapterIcon
        adapterIcon.submitList(arrApp)

        adapterIconsChanger = IconsAdapter()
        binding.rcvIconChanger.adapter = adapterIconsChanger
        arrIconChanger.addAll(arrIcon.flatMap { it.path }.filter { it.contains("avatar") })
        adapterIconsChanger.submitList(arrIconChanger)


    }

    override fun setClick() {
        adapterIcon.onClick = {

        }
        adapterIconsChanger.onCLick = {

        }
    }
}