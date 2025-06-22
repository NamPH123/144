package com.namseox.st144_icon_changer.ui.changeicon

import android.view.View
import androidx.camera.core.processing.SurfaceProcessorNode.In
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseAdapter
import com.namseox.st144_icon_changer.base.AbsBaseDiffCallBack
import com.namseox.st144_icon_changer.databinding.ItemChangeIconsBinding
import com.namseox.st144_icon_changer.model.ChangeIconModel
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.onSingleClick

class ChangeIconsAdapter :
    AbsBaseAdapter<ChangeIconModel, ItemChangeIconsBinding>(R.layout.item_change_icons, ChangeIconDiff()) {
        var onClick : ((Int,String) -> Unit)? = null
    class ChangeIconDiff : AbsBaseDiffCallBack<ChangeIconModel>() {
        override fun itemsTheSame(oldItem: ChangeIconModel, newItem: ChangeIconModel): Boolean {
            return oldItem == newItem
        }

        override fun contentsTheSame(oldItem: ChangeIconModel, newItem: ChangeIconModel): Boolean {
            return oldItem != newItem
        }
    }

    override fun bind(
        binding: ItemChangeIconsBinding,
        position: Int,
        data: ChangeIconModel,
        holder: RecyclerView.ViewHolder
    ) {
        Glide.with(binding.imv).load(ASSET + data.path).into(binding.imv)
        if(data.check){
            binding.imvDelete.visibility = View.VISIBLE
            binding.sw.setImageResource(R.drawable.ic_swith_true_per)
        }else{
            binding.imvDelete.visibility = View.GONE
            binding.sw.setImageResource(R.drawable.ic_swith_false_per)
        }
        if(data.app==null){
            binding.imvDelete.visibility = View.GONE
            binding.imvPlus.setImageResource(R.drawable.imv_plus)
        }else{
            binding.imvDelete.visibility = View.VISIBLE
            binding.imvPlus.setImageDrawable(data.app!!.icon)
        }
        binding.imvDelete.onSingleClick {
            onClick?.invoke(position,"delete")
        }
        binding.imvPlus.onSingleClick {
            onClick?.invoke(position,"plus")
        }
        binding.sw.onSingleClick {
            onClick?.invoke(position,"sw")
        }
    }
}