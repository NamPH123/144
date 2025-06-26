package com.namseox.st144_icon_changer.ui.editphoto

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemMyIconsBinding
import com.namseox.st144_icon_changer.model.FilterModel
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.show

class FilterAdapter :
    AbsBaseAdapter<FilterModel, ItemMyIconsBinding>(R.layout.item_my_icons, FilterDiff()) {
    var onClick: ((Int) -> Unit)? = null
    var pos = 0
    override fun bind(
        binding: ItemMyIconsBinding,
        position: Int,
        data: FilterModel,
        holder: RecyclerView.ViewHolder
    ) {
        Glide.with(binding.root).load(data.imv1).into(binding.imv)
        if (pos == position) {
            binding.bg.show()
        } else {
            binding.bg.hide()
        }
        binding.imv.onSingleClick {
            onClick?.invoke(position)
        }
    }

    class FilterDiff : AbsBaseDiffCallBack<FilterModel>() {
        override fun itemsTheSame(
            oldItem: FilterModel,
            newItem: FilterModel
        ): Boolean {
            return oldItem.check == newItem.check
        }

        override fun contentsTheSame(
            oldItem: FilterModel,
            newItem: FilterModel
        ): Boolean {
            return oldItem.check != newItem.check
        }

    }
}