package com.namseox.st144_icon_changer.ui.main.icons

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemHomeIconsChangerBinding
import com.namseox.st144_icon_changer.utils.Const.ASSET

class IconsAdapter : AbsBaseAdapter<String, ItemHomeIconsChangerBinding>(
    R.layout.item_home_icons_changer,
    IconDiffCallBack()
) {
    var onCLick: ((String) -> Unit)? = null
    override fun bind(
        binding: ItemHomeIconsChangerBinding,
        position: Int,
        data: String,
        holder: RecyclerView.ViewHolder
    ) {
        Glide.with(binding.imv).load(ASSET + data).into(binding.imv)
        binding.imv.setOnClickListener {
            onCLick?.invoke(data)
        }
    }

    class IconDiffCallBack : AbsBaseDiffCallBack<String>() {
        override fun itemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
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