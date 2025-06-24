package com.namseox.st144_icon_changer.ui.editphoto

import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityEditPhotoBinding
import com.namseox.st144_icon_changer.utils.onSingleClick
import java.io.File

class EditPhotoActivity : AbsBaseActivity<ActivityEditPhotoBinding>() {
    lateinit var adapterShape : ShapeAdapter
    var arrListShape = arrayListOf(
        R.drawable.ic_shape_1,
        R.drawable.ic_shape_2,
        R.drawable.ic_shape_3,
        R.drawable.ic_shape_4,
        R.drawable.ic_shape_5,
        R.drawable.ic_shape_6,
    )
    override fun getLayoutId(): Int = R.layout.activity_edit_photo

    override fun initView() {
        Glide.with(this).load(File(filesDir, "cache/cache.png")).into(binding.imvBG)
        adapterShape = ShapeAdapter()
        binding.rcvMyIcons.adapter = adapterShape
        binding.rcvMyIcons.itemAnimator = null
        adapterShape.submitList(arrListShape)
    }

    override fun initAction() {
        binding.apply {
            imvReset.onSingleClick {
                imvBG.scaleX = 1f
            }
            imvRevert.onSingleClick {
                imvBG.scaleX = -imvBG.scaleX
            }
        }
        adapterShape.onClick = {

        }
    }
}