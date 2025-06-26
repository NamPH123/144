package com.namseox.st144_icon_changer.ui.gallery

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemGalleryBinding
import com.namseox.st144_icon_changer.model.GalleryModel

class GalleryAdapter :
    AbsBaseAdapter<GalleryModel, ItemGalleryBinding>(R.layout.item_gallery, GalleryDiff()) {
    var onClick: ((Int) -> Unit)? = null
    override fun bind(
        binding: ItemGalleryBinding,
        position: Int,
        data: GalleryModel,
        holder: RecyclerView.ViewHolder
    ) {
        binding.tvFolder.text = data.folder
        binding.tvSize.text = data.arrPath.size.toString()
        Glide.with(binding.imv).load(data.arrPath[0]).into(binding.imv)
        binding.root.setOnClickListener {
            onClick?.invoke(position)
        }
    }

    class GalleryDiff : AbsBaseDiffCallBack<GalleryModel>() {
        override fun itemsTheSame(
            oldItem: GalleryModel,
            newItem: GalleryModel
        ): Boolean {
            return oldItem.folder == newItem.folder
        }

        override fun contentsTheSame(
            oldItem: GalleryModel,
            newItem: GalleryModel
        ): Boolean {
            return oldItem.folder != newItem.folder
        }

    }
}