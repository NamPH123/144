package com.namseox.st144_icon_changer.ui.main.icons

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseFragment
import com.namseox.st144_icon_changer.databinding.FragmentIconsBinding
import com.namseox.st144_icon_changer.model.AppInfoModel
import com.namseox.st144_icon_changer.ui.changeicon.ChangeIconsActivity
import com.namseox.st144_icon_changer.ui.editicons.EditIconsActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.DataHelper.arrIcon
import com.namseox.st144_icon_changer.utils.DataHelper.mutableLiveData
import com.namseox.st144_icon_changer.utils.DataHelper.searchApps
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import java.io.File

class IconsFragment : AbsBaseFragment<FragmentIconsBinding, MainActivity>() {
    lateinit var adapterIcon: AppAdapter
    lateinit var adapterIconsChanger: IconsAdapter
    var arrSubApp = arrayListOf<AppInfoModel>()

    var arrIconChanger = arrayListOf<String>()
    override fun getLayout(): Int = R.layout.fragment_icons

    override fun initView() {
        arrSubApp.addAll(arrApp)
        adapterIcon = AppAdapter()
        binding.rcvApp.adapter = adapterIcon
        binding.rcvApp.itemAnimator = null
        adapterIcon.submitList(arrSubApp)

        adapterIconsChanger = IconsAdapter()
        binding.rcvIconChanger.adapter = adapterIconsChanger
        binding.rcvIconChanger.itemAnimator = null
        mutableLiveData.observe(this){
            if(it==1){
                arrSubApp.clear()
                arrSubApp.addAll(arrApp)
                adapterIcon.submitList(arrSubApp)


                arrIconChanger.addAll(arrIcon.flatMap { it.path }.filter { it.contains("avatar") })
                adapterIconsChanger.submitList(arrIconChanger)
            }
        }

    }

    override fun setClick() {
        binding.apply {
            tvSearch.onSingleClick { binding.tvSearch.isCursorVisible = true }
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
            binding.tvSearch.isCursorVisible = false
            startActivity(
                newIntent(requireContext(), EditIconsActivity::class.java).putExtra(DATA, arrApp.indexOf(it))
            )
        }
        adapterIconsChanger.onCLick = {
            binding.tvSearch.isCursorVisible = false
            startActivity(newIntent(requireContext(), ChangeIconsActivity::class.java).putExtra("pos",it))
        }
    }
}