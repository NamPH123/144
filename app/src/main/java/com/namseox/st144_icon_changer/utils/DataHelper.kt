package com.namseox.st144_icon_changer.utils

import android.content.Context
import com.namseox.st144_icon_changer.R
import com.namseox.st144_icon_changer.model.AppInfoModel
import com.namseox.st144_icon_changer.model.IconModel
import com.namseox.st144_icon_changer.model.LanguageModel

object DataHelper {
    var arrTheme = arrayListOf<String>()
    var arrIcon = arrayListOf<IconModel>()
    var arrBG = arrayListOf<IconModel>()
    var arrApp = arrayListOf<AppInfoModel>()

    fun Context.getData() {
        var assetManager = assets

        arrTheme.clear()
        arrIcon.clear()
        arrBG.clear()
        arrApp.clear()

        val subBG = assetManager.list("bg")
        val subIcon = assetManager.list("icons")
        val subThemes = assetManager.list("themes")

        for (subFolder in subBG!!) {
            arrBG.add(
                IconModel(
                    subFolder,
                    assetManager.list("bg/$subFolder")!!
                        .map { "bg/$subFolder/$it" } as ArrayList<String>))
        }

        for (subFolder in subIcon!!) {
            arrIcon.add(
                IconModel(
                    subFolder,
                    assetManager.list("icons/$subFolder")!!
                        .map { "icons/$subFolder/$it" } as ArrayList<String>))
        }

        for (subFolder in subThemes!!) {
            arrTheme.add("themes/$subFolder")
        }

        arrApp.addAll(getAllLaunchableApps())
    }

    var positionLanguageOld: Int = 0
    var listLanguage = arrayListOf<LanguageModel>(
        LanguageModel("Spanish", "es", R.drawable.ic_flag_spanish),
        LanguageModel("French", "fr", R.drawable.ic_flag_french),
        LanguageModel("Hindi", "hi", R.drawable.ic_flag_hindi),
        LanguageModel("English", "en", R.drawable.ic_flag_english),
        LanguageModel("Portuguese", "pt", R.drawable.ic_flag_portugeese),
        LanguageModel("German", "de", R.drawable.ic_flag_germani),
        LanguageModel("Indonesian", "in", R.drawable.ic_flag_indo)
    )
    fun searchApps(query: String): List<AppInfoModel> {
        return arrApp.filter { it.name.contains(query, ignoreCase = true) }
    }
}