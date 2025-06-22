package com.namseox.st144_icon_changer.ui.changeicon

import androidx.recyclerview.widget.RecyclerView
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemHomeAppBinding
import com.namseox.st144_icon_changer.model.AppInfoModel
import com.namseox.st144_icon_changer.utils.onSingleClick

class AppAdapter :
    AbsBaseAdapter<AppInfoModel, ItemHomeAppBinding>(R.layout.item_home_app, AppDiff()) {
        var onClick : ((Int)->Unit)? = null
    class AppDiff : AbsBaseDiffCallBack<AppInfoModel>() {
        override fun itemsTheSame(oldItem: AppInfoModel, newItem: AppInfoModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun contentsTheSame(oldItem: AppInfoModel, newItem: AppInfoModel): Boolean {
            return oldItem.name != newItem.name
        }
    }

    override fun bind(
        binding: ItemHomeAppBinding,
        position: Int,
        data: AppInfoModel,
        holder: RecyclerView.ViewHolder
    ) {
        binding.tv.text = data.name
        binding.imv.setImageDrawable(data.icon)
        binding.root.onSingleClick {
            onClick?.invoke(position)
        }
    }
}