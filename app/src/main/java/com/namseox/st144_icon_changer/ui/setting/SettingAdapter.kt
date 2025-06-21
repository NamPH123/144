package com.namseox.st144_icon_changer.ui.setting

import androidx.recyclerview.widget.RecyclerView
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemSettingBinding
import com.namseox.st144_icon_changer.model.SettingModel
import com.namseox.st144_icon_changer.utils.onSingleClick

class SettingAdapter : AbsBaseAdapter<SettingModel, ItemSettingBinding>(R.layout.item_setting, DiffSetting()) {
    var onClick : ((Int) -> Unit)? = null
    class DiffSetting : AbsBaseDiffCallBack<SettingModel>(){
        override fun itemsTheSame(oldItem: SettingModel, newItem: SettingModel): Boolean {
            return oldItem.imv == newItem.imv
        }

        override fun contentsTheSame(oldItem: SettingModel, newItem: SettingModel): Boolean {
            return oldItem.imv != newItem.imv
        }

    }

    override fun bind(
        binding: ItemSettingBinding,
        position: Int,
        data: SettingModel,
        holder: RecyclerView.ViewHolder
    ) {
        binding.apply {
            imv.setImageResource(data.imv)
            tv.text = root.context.getString(data.text)
            root.onSingleClick { onClick?.invoke(data.imv) }
        }
    }
}