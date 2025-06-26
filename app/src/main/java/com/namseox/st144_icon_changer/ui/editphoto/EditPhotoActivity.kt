package com.namseox.st144_icon_changer.ui.editphoto

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityEditPhotoBinding
import com.namseox.st144_icon_changer.model.FilterModel
import com.namseox.st144_icon_changer.ui.editicons.EditIconsActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.hide
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.saveBitmap
import com.namseox.st144_icon_changer.utils.show
import com.namseox.st144_icon_changer.utils.viewToBitmap

class EditPhotoActivity : AbsBaseActivity<ActivityEditPhotoBinding>() {
    var path = ""
    var arrPaint = arrayListOf<ColorMatrixColorFilter>()
    var arrFillter = arrayListOf<FilterModel>()
    var adapterFilter = FilterAdapter()
    lateinit var adapterShape : ShapeAdapter
    var arrListShape = arrayListOf(
        R.drawable.ic_shape_1,
        R.drawable.ic_shape_2,
        R.drawable.ic_shape_3,
        R.drawable.ic_shape_4,
        R.drawable.ic_shape_5,
        R.drawable.ic_shape_6,
    )
    var arrListImv = arrayListOf(
        R.drawable.ic_shape_1_a,
        R.drawable.ic_shape_2_a,
        R.drawable.ic_shape_3_a,
        R.drawable.ic_shape_4_a,
        R.drawable.ic_shape_5_a,
        R.drawable.ic_shape_6_a,
    )
    override fun getLayoutId(): Int = R.layout.activity_edit_photo

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            path = intent.getStringExtra(DATA).toString()
            arrPaint = arrayListOf(
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            0.393f, 0.769f, 0.189f, 0.0f,
                            0.0f, 0.349f, 0.686f, 0.168f,
                            0.0f, 0.0f, 0.272f, 0.534f,
                            0.131f, 0.0f, 0.0f, 0.0f,
                            0.0f, 0.0f, 1.0f, 0.0f,
                            0.0f, 0.0f, 0.0f, 0.0f, 1.0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            0.393f, 0.769f, 0.189f, 0.0f,
                            0.0f, 0.349f, 0.686f, 0.168f,
                            0.0f, 0.0f, 0.272f, 0.534f,
                            0.131f, 0.0f, 0.0f, 0.0f,
                            0.0f, 0.0f, 1.0f, 0.0f,
                            0.0f, 0.0f, 0.0f, 0.0f, 1.0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            -0.36f, 1.691f, -0.32f, 0f, 0f,
                            0.325f, 0.398f, 0.275f, 0f, 0f,
                            0.79f, 0.796f, -0.76f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            0.14f, 0.45f, 0.05f, 0f, 0f,
                            0.12f, 0.39f, 0.04f, 0f, 0f,
                            0.08f, 0.28f, 0.03f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            -0.41f, 0.539f, -0.873f, 0f, 0f,
                            0.452f, 0.666f, -0.11f, 0f, 0f,
                            -0.3f, 1.71f, -0.4f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            0f, 1f, 0f, 0f, 0f,
                            0f, 1f, 0f, 0f, 0f,
                            0f, 1f, 0f, 0f, 0f,
                            0f, 1f, 0f, 1f, 0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            0.14f, 0.45f, 0.05f, 0f, 0f,
                            0.12f, 0.39f, 0.04f, 0f, 0f,
                            0.08f, 0.28f, 0.03f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                ),
                ColorMatrixColorFilter(
                    ColorMatrix(
                        floatArrayOf(
                            1f, 0f, 0f, 0f, 0f,
                            0f, 2f, 0f, 0f, 0f,
                            0f, 0f, 0f, 0.5f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                ),
            )
            arrFillter = arrayListOf(
                FilterModel(R.drawable.ic_effect_none, true),
                FilterModel(R.drawable.ic_effect_noir),
                FilterModel(R.drawable.ic_effect_warm),
                FilterModel(R.drawable.ic_effect_sepia),
                FilterModel(R.drawable.ic_effect_cold),
                FilterModel(R.drawable.ic_effect_classic),
                FilterModel(R.drawable.ic_effect_monochrome),
                FilterModel(R.drawable.ic_effect_green)
            )
            Glide.with(this).load(path).into(binding.imvBG)
            adapterShape = ShapeAdapter()
            binding.rcvMyIcons.adapter = adapterShape
            binding.rcvMyIcons.itemAnimator = null
            adapterShape.submitList(arrListShape)

            binding.rcvFilter.adapter = adapterFilter
            binding.rcvFilter.itemAnimator = null
            adapterFilter.submitList(arrFillter)
        }

    }

    override fun initAction() {
        binding.apply {
            llLoading.onSingleClick { }
            tvApply.onSingleClick {
                llLoading.show()
                saveBitmap(this@EditPhotoActivity, viewToBitmap(rl), "test") { it, path ->
                    llLoading.hide()
                    startActivity(newIntent(applicationContext, EditIconsActivity::class.java))
                    finish()
                }
            }
            imvBack.onSingleClick { finish() }
            imvReset.onSingleClick {
                imvBG.scaleX = 1f
            }
            imvRevert.onSingleClick {
                imvBG.scaleX = -imvBG.scaleX
            }
        }
        adapterShape.onClick = {
            adapterShape.pos = it
            adapterShape.submitList(arrListShape)
            Glide.with(applicationContext).load(arrListImv[it]).into(binding.imv)
        }
        adapterFilter.onClick = {
            adapterFilter.pos = it
            adapterFilter.submitList(arrFillter)
            if (it == 0) {
                binding.imvBG.colorFilter = null
            } else {
                binding.imvBG.colorFilter = arrPaint[it]
            }

        }
    }
}