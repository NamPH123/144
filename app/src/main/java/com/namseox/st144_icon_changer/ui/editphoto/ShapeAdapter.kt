package com.namseox.st144_icon_changer.ui.editphoto

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemShapeBinding
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.show

class ShapeAdapter : AbsBaseAdapter<Int, ItemShapeBinding>(R.layout.item_shape, ShapeDiff()) {
    var pos = 0
    var onClick: ((Int) -> Unit)? = null

    class ShapeDiff : AbsBaseDiffCallBack<Int>() {
        override fun itemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun contentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem != newItem
        }

    }

    override fun bind(
        binding: ItemShapeBinding,
        position: Int,
        data: Int,
        holder: RecyclerView.ViewHolder
    ) {
        binding.imv.setImageResource(data)
        if (position == pos) {
            binding.imv.imageTintList = ColorStateList.valueOf("#E09CD5".toColorInt())
            binding.imvTick.show()
        } else {
            binding.imv.imageTintList = ColorStateList.valueOf("#ffffff".toColorInt())
            binding.imvTick.hide()
        }
        binding.imv.onSingleClick {
            if (position != pos) {
                onClick?.invoke(position)
            }
        }
    }
}