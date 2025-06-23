package com.namseox.st144_icon_changer.ui.editicons

import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemChooseIconBinding
import com.namseox.st144_icon_changer.utils.Const.ASSET

class PathCategoryAdapter :
    AbsBaseAdapter<String, ItemChooseIconBinding>(R.layout.item_choose_icon, PathCategoryDiff()) {
    var pos = -1
    var onClick: ((Int) -> Unit)? = null
    override fun bind(
        binding: ItemChooseIconBinding,
        position: Int,
        data: String,
        holder: RecyclerView.ViewHolder
    ) {
        Glide.with(binding.imv).load(ASSET + data).into(binding.imv)
        binding.imv.setOnClickListener {
            onClick?.invoke(position)
        }
        if (pos == position) {
            binding.bg.setBackgroundResource(R.drawable.bg_choose_icon)
        } else {
            binding.bg.setBackgroundColor("#ffffff".toColorInt())
        }
    }

    class PathCategoryDiff : AbsBaseDiffCallBack<String>() {
        override fun itemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun contentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem != newItem
        }

    }
}