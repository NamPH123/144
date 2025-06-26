package com.namseox.st144_icon_changer.ui.gallery

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityGalleryBinding
import com.namseox.st144_icon_changer.model.GalleryModel
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.subgallery.SubGalleryActivity
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.getAllFile
import com.namseox.st144_icon_changer.utils.getAllVideoFolders
import com.namseox.st144_icon_changer.utils.isImageFile
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.putParcelableExtra
import java.io.File

class GalleryActivity : AbsBaseActivity<ActivityGalleryBinding>() {
    lateinit var adapter: GalleryAdapter
    lateinit var file: File
    var arrGallery = ArrayList<GalleryModel>()
    override fun getLayoutId(): Int = R.layout.activity_gallery

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            getAllVideoFolders(application).forEachIndexed { i, data ->
                file = File(data)
                arrGallery.add(GalleryModel(file.name, arrayListOf()))
                getAllFile(file).takeIf { it.isNotEmpty() }?.let { files ->
                    files.forEach {
                        if (isImageFile(it)) {
                            arrGallery.last().arrPath.add(it)
                        }
                    }
                }
            }
            adapter = GalleryAdapter()
            binding.rcv.adapter = adapter
            binding.rcv.itemAnimator = null
            adapter.submitList(arrGallery)
        }

    }

    override fun initAction() {
        adapter.onClick = {
            startActivity(
                newIntent(
                    applicationContext,
                    SubGalleryActivity::class.java
                ).putParcelableExtra(DATA, arrGallery[it]).apply {
                    if (intent.getStringExtra(DATA) == ICON) {
                        putExtra(ICON, ICON)
                    }
                }
            )
        }
        binding.apply {
            imvBack.onSingleClick { finish() }
        }
    }
}