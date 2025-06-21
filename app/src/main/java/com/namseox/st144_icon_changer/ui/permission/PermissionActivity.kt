package com.namseox.st144_icon_changer.ui.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.View
import androidx.core.app.ActivityCompat
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivityPermissionBinding
import com.namseox.st144_icon_changer.ui.main.MainActivity
import com.namseox.st144_icon_changer.utils.Const
import com.namseox.st144_icon_changer.utils.Const.REQUEST_NOTIFICATION_PERMISSION
import com.namseox.st144_icon_changer.utils.Const.REQUEST_STORAGE_PERMISSION
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import com.namseox.st144_icon_changer.utils.changeText
import com.namseox.st144_icon_changer.utils.checkPermision
import com.namseox.st144_icon_changer.utils.checkUsePermision
import com.namseox.st144_icon_changer.utils.onSingleClick
import com.namseox.st144_icon_changer.utils.requesPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PermissionActivity: AbsBaseActivity<ActivityPermissionBinding>() {
    var check = false
    var count = 0
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    override fun getLayoutId(): Int = R.layout.activity_permission
    override fun initView() {
        count = sharedPreferenceUtils.getNumber("count")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            binding.rl4.visibility = View.VISIBLE
            binding.rl2.visibility = View.GONE
        } else {
            binding.rl4.visibility = View.GONE
            binding.rl2.visibility = View.VISIBLE
        }
        val space = " "
        binding.tvTitle.text = TextUtils.concat(
            changeText(
                this,
                getString(R.string.allow),
                Color.parseColor("#2F2F2F"),
                R.font.roboto_regular
            ),space,
            changeText(
                this,
                getString(R.string.app_name),
                Color.parseColor("#E09CD5"),
                R.font.roboto_bold
            ),
            "\n",
            changeText(
                this,
                getString(R.string.request_permission_to_use_notifications_to_notify_you),
                Color.parseColor("#2F2F2F"),
                R.font.roboto_regular
            ),
        )
        checkPer()
    }

    override fun initAction() {

        binding.btnContinue.onSingleClick {
                sharedPreferenceUtils.putBooleanValue(Const.PERMISON, true)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
                finish()
        }
        binding.swiVibrate2.onSingleClick {
            if (!checkPermision(this)) {
                ActivityCompat.requestPermissions(
                    this,
                    checkUsePermision(),
                    Const.REQUEST_STORAGE_PERMISSION
                )
            }
        }
        binding.swiVibrate4.onSingleClick {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        count ++
        if(count>1){
            sharedPreferenceUtils.putNumber("count",count)
            when (requesPermission(requestCode, permissions, grantResults)) {
                REQUEST_STORAGE_PERMISSION ->{
                    binding.swiVibrate2.setImageResource(R.drawable.ic_swith_true_per)
                }
                REQUEST_NOTIFICATION_PERMISSION -> {
                    binding.swiVibrate4.setImageResource(R.drawable.ic_swith_true_per)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkPer()
    }
    private fun checkPer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                binding.swiVibrate4.setImageResource(R.drawable.ic_swith_true_per)
            } else {
                binding.swiVibrate4.setImageResource(R.drawable.ic_swith_false_per)
            }
        }else{
            if (checkPermision(this)) {
                binding.swiVibrate2.setImageResource(R.drawable.ic_swith_true_per)
            } else {
                binding.swiVibrate2.setImageResource(R.drawable.ic_swith_false_per)
            }
        }
    }
}