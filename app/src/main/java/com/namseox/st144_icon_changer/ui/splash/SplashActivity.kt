package com.namseox.st144_icon_changer.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.base.AbsBaseActivity
import com.namseox.st144_icon_changer.databinding.ActivitySplashBinding
import com.namseox.st144_icon_changer.ui.language.LanguageActivity
import com.namseox.st144_icon_changer.ui.tutorial.TutorialActivity
import com.namseox.st144_icon_changer.utils.Const
import com.namseox.st144_icon_changer.utils.DataHelper.getData
import com.namseox.st144_icon_changer.utils.SharedPreferenceUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AbsBaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    override fun getLayoutId(): Int = R.layout.activity_splash
    var handle = Handler(Looper.getMainLooper())
    var runable = Runnable {
        if (!SharedPreferenceUtils.getInstance(applicationContext)
                .getBooleanValue(Const.LANGUAGE)
        ) {
            startActivity(Intent(this@SplashActivity, LanguageActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, TutorialActivity::class.java))
        }
        finish()
    }

    override fun initView() {
        if (!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
            && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN)
        ) {
            finish(); return;
        }
        CoroutineScope(Dispatchers.IO).launch {
            getData()
        }

        handle.postDelayed(runable, 3000)
    }

    override fun onRestart() {
        super.onRestart()
        handle.postDelayed(runable, 2000)
    }

    override fun onBackPressed() {

    }

    override fun onStop() {
        super.onStop()
        handle.removeCallbacks(runable)
    }

    override fun initAction() {
    }
}