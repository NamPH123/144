package com.namseox.st144_icon_changer.ui.subgallery

import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivitySubFolderBinding
import com.namseox.st144_icon_changer.model.GalleryModel
import com.namseox.st144_icon_changer.ui.editphoto.EditPhotoActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.setwallpaper.SetwallpaperActivity
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.ICON
import com.namseox.st144_icon_changer.utils.DataHelper.arrApp
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick

class SubGalleryActivity : AbsBaseActivity<ActivitySubFolderBinding>() {
    lateinit var mGalleryModel: GalleryModel
    lateinit var adapter: WallpaperAdapter
    override fun getLayoutId(): Int = R.layout.activity_sub_folder

    override fun initView() {
        if(arrApp.size==0){
            startActivity(newIntent(applicationContext,MainActivity::class.java))
        }else{
            mGalleryModel = intent.getParcelableExtra(DATA)!!
            binding.tvTitle.text = mGalleryModel.folder

            adapter = WallpaperAdapter()
            binding.rcv.adapter = adapter
            binding.rcv.itemAnimator = null
            adapter.submitList(mGalleryModel.arrPath)
        }

    }

    override fun initAction() {
        binding.imvBack.onSingleClick { finish() }
        adapter.onClick = {
            if (intent.getStringExtra(ICON) == ICON){
                startActivity(newIntent(applicationContext, EditPhotoActivity::class.java).putExtra(DATA,mGalleryModel.arrPath[it]))
            }else{
                startActivity(
                    newIntent(applicationContext, SetwallpaperActivity::class.java).putExtra(
                        DATA,
                        mGalleryModel.arrPath[it]
                    )
                )
            }
        }
    }
}