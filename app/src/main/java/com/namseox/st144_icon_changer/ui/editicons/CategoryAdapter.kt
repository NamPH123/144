package com.namseox.st144_icon_changer.ui.editicons

import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemChooseIconCategoryBinding
import com.namseox.st144_icon_changer.utils.onSingleClick

class CategoryAdapter : AbsBaseAdapter<String, ItemChooseIconCategoryBinding>(
    R.layout.item_choose_icon_category,
    CategoryDiff()
) {
    var pos = 0
    var onClick: ((Int) -> Unit)? = null

    class CategoryDiff : AbsBaseDiffCallBack<String>() {
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

    override fun bind(
        binding: ItemChooseIconCategoryBinding,
        position: Int,
        data: String,
        holder: RecyclerView.ViewHolder
    ) {
        binding.tv.text = data
        binding.root.onSingleClick {
            if(pos != position){
                onClick?.invoke(position)
            }
        }
        if (pos == position) {
            binding.tv.setTextColor("#FFFFFF".toColorInt())
            binding.tv.setBackgroundResource(R.drawable.bg_choose_category_true)
        } else {
            binding.tv.setTextColor("#E09CD5".toColorInt())
            binding.tv.setBackgroundResource(R.drawable.bg_choose_category_false)
        }
    }
}