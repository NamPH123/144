package com.namseox.st144_icon_changer.ui.main.icons

import androidx.recyclerview.widget.RecyclerView
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemHomeAppBinding
import com.namseox.st144_icon_changer.model.AppInfo
import com.namseox.st144_icon_changer.utils.onSingleClick

class AppAdapter : AbsBaseAdapter<AppInfo, ItemHomeAppBinding>(R.layout.item_home_app,AppDiffCallBack()) {
    var onClick: ((AppInfo) -> Unit)? = null
    override fun bind(
        binding: ItemHomeAppBinding,
        position: Int,
        data: AppInfo,
        holder: RecyclerView.ViewHolder
    ) {
        binding.tv.text = data.name
        binding.imv.setImageDrawable(data.icon)
        binding.root.onSingleClick {
            onClick?.invoke(data)
        }
    }

    class AppDiffCallBack : AbsBaseDiffCallBack<AppInfo>(){
        override fun itemsTheSame(
            oldItem: AppInfo,
            newItem: AppInfo
        ): Boolean {
           return oldItem.packageName == newItem.packageName
        }

        override fun contentsTheSame(
            oldItem: AppInfo,
            newItem: AppInfo
        ): Boolean {
            return oldItem.packageName != newItem.packageName
        }

    }
}