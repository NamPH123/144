package com.namseox.st144_icon_changer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryModel(var folder: String, var arrPath: ArrayList<String>) : Parcelable