package com.namseox.st144_icon_changer.ui.subgallery

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemSubFolderBinding
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.onSingleClick

class WallpaperAdapter : AbsBaseAdapter<String, ItemSubFolderBinding>(R.layout.item_sub_folder,WallpaperDiff()) {
    var onClick : ((Int)->Unit)? = null
    class WallpaperDiff : AbsBaseDiffCallBack<String>() {
        override fun itemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun contentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem != newItem
        }

    }

    override fun bind(
        binding: ItemSubFolderBinding,
        position: Int,
        data: String,
        holder: RecyclerView.ViewHolder
    ) {
        binding.imv.onSingleClick {
            onClick?.invoke(position)
        }
        Log.d(TAG, "bind000000: $data")
        Glide.with(binding.imv).load(data).into(binding.imv)
    }
}