package com.namseox.st144_icon_changer.utils


import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.model.LanguageModel
import androidx.core.graphics.toColorInt

@BindingAdapter("setBGCV")
fun ConstraintLayout.setBGCV(check: LanguageModel) {
    if (check.active) {
        this.setBackgroundResource(R.drawable.bg_card_border2)
    } else {
        this.setBackgroundResource(R.drawable.bg_card_border)
    }
}

@BindingAdapter("setSrcCheckLanguage")
fun AppCompatImageView.setSrcCheckLanguage(check: Boolean) {
    if (check) {
        this.setImageResource(R.drawable.ic_check_language_true)
    } else {
        this.setImageResource(R.drawable.ic_check_language_false)
    }
}

@BindingAdapter("setTextColor")
fun TextView.setTextColor(check: Boolean) {
    if (check) {
        this.setTextColor("#FFFFFF".toColorInt())
    } else {
        this.setTextColor("#242424".toColorInt())
    }
}
@BindingAdapter("setBG")
fun AppCompatImageView.setBG(id: Int) {
    Glide.with(this).load(id).into(this)
}
@BindingAdapter("setImg")
fun AppCompatImageView.setImg(data : Int){
    Glide.with(this).load(data).into(this)
}