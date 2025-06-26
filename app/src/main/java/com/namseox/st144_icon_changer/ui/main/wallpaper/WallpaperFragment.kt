package com.namseox.st144_icon_changer.ui.main.wallpaper

import android.util.Log
import androidx.core.app.ActivityCompat
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseFragment
import com.namseox.st144_icon_changer.databinding.FragmentWallpaperBinding
import com.namseox.st144_icon_changer.ui.category.CategoryActivity
import com.namseox.st144_icon_changer.ui.gallery.GalleryActivity
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.ui.setwallpaper.SetwallpaperActivity
import com.namseox.st144_icon_changer.utils.Const
import com.namseox.st144_icon_changer.utils.Const.ASSET
import com.namseox.st144_icon_changer.utils.Const.DATA
import com.namseox.st144_icon_changer.utils.Const.REQUEST_STORAGE_PERMISSION
import com.namseox.st144_icon_changer.utils.DataHelper.arrBG
import com.namseox.st144_icon_changer.utils.DataHelper.mutableLiveData
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.checkUsePermision
import com.namseox.st144_icon_changer.utils.newIntent
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.requesPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WallpaperFragment : AbsBaseFragment<FragmentWallpaperBinding, MainActivity>() {
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    var arrString = arrayListOf<String>()
    lateinit var adapter: WallpaperAdapter
    override fun getLayout(): Int = R.layout.fragment_wallpaper

    override fun initView() {
        adapter = WallpaperAdapter()
        binding.rcv.adapter = adapter
        binding.rcv.itemAnimator = null
        mutableLiveData.observe(this){
            if(it==1){
                arrBG.forEach {
                    arrString.add(it.path[0])
                }
                adapter.submitList(arrString)
            }
        }
    }

    override fun setClick() {
        adapter.onClick = {
            startActivity(
                newIntent(requireContext(), SetwallpaperActivity::class.java).putExtra(
                    DATA,
                    ASSET+ arrString[it]
                )
            )
        }
        binding.apply {
            imvCategory.onSingleClick {
                startActivity(newIntent(requireContext(), CategoryActivity::class.java))
            }
            imvFromGallery.onSingleClick {
                if (!checkPermision(requireContext())) {
                    ActivityCompat.requestPermissions(
                        activity,
                        checkUsePermision(),
                        Const.REQUEST_STORAGE_PERMISSION
                    )
                } else {
                    startActivity(newIntent(requireContext(), GalleryActivity::class.java))
                }

            }
        }
    }
}