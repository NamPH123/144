package com.namseox.st144_icon_changer.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPreferenceUtils @Inject constructor(context: Context) {
    private val MYAPPLICATION = "MY_APPLICATION"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(MYAPPLICATION, Context.MODE_PRIVATE)



    //lưu đối tượng
    inline fun <reified T> getObjModel(): T? {
        val value = getStringValue("Key_Obj_${T::class.java.name}")
        return if (value != null && value.isNotEmpty()) {
            Gson().fromJson(value, T::class.java)
        } else {
            null
        }
    }

    inline fun <reified T> setObjModel(value: T?) {
        if (value != null) {
            putStringValue("Key_Obj_${T::class.java.name}", Gson().toJson(value))
        }
    }
    fun putStringValue(key: String?, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value).apply()
    }

    fun getStringValue(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }


    //check number name playlist
    fun putNumber(key: String, value : Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value).apply()
    }

    fun getNumber(key: String): Int {
        return sharedPreferences.getInt(key,0)
    }

    //
    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
    fun putBooleanValue(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value).apply()
    }
    fun putStringArray(key: String, array: ArrayList<String>) {
        val jsonString = Gson().toJson(array)
        val editor = sharedPreferences!!.edit()
        editor.putString(key, jsonString)
        editor.apply()
    }

    fun getStringArray(key: String): ArrayList<String> {
        val jsonString = sharedPreferences!!.getString(key, null)
        if (jsonString != null) {
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return Gson().fromJson(jsonString, type)
        }
        return arrayListOf()
    }

//    fun putSongRecentlyModelArray(key: String, array: ArrayList<SongRecentlyModel>) {
//        val jsonString = Gson().toJson(array)
//        val editor = sharedPreferences!!.edit()
//        editor.putString(key, jsonString)
//        editor.apply()
//    }
//
//    fun getSongRecentlyModelArray(key: String): ArrayList<SongRecentlyModel> {
//        val jsonString = sharedPreferences!!.getString(key, null)
//        if (jsonString != null) {
//            val type = object : TypeToken<ArrayList<SongRecentlyModel>>() {}.type
//            return Gson().fromJson(jsonString, type)
//        }
//        return arrayListOf()
//    }


    companion object : SingletonHolder<SharedPreferenceUtils, Context>(::SharedPreferenceUtils)
}
