package com.namseox.st144_icon_changer.ui.editicons

import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemMyIconsBinding
import com.namseox.st144_icon_changer.utils.onSingleClick

class MyIconsAdapter :
    AbsBaseAdapter<String, ItemMyIconsBinding>(R.layout.item_my_icons, MyIconsDiff()) {
    var onClick: ((Int) -> Unit)? = null
    var pos = -1
    override fun bind(
        binding: ItemMyIconsBinding,
        position: Int,
        data: String,
        holder: RecyclerView.ViewHolder
    ) {
        Glide.with(binding.root.context).load(data).into(binding.imv)
        if (pos == position) {
            binding.bg.setBackgroundColor("#FBF1F8".toColorInt())
        } else {
            binding.bg.setBackgroundResource(R.drawable.bg_my_icons)
        }
        binding.root.onSingleClick { onClick?.invoke(position) }
    }

    class MyIconsDiff : AbsBaseDiffCallBack<String>() {
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