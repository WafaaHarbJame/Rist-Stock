package com.app.riststock.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPManger(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("RistSettings", 0)

    fun setData(key: String?, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit();
    }

    fun setData(key: String?, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
        editor.commit();
    }

    fun setData(key: String?, value: Float) {
        val editor = preferences.edit()
        editor.putFloat(key, value)
        editor.apply()
        editor.commit();
    }
    fun setData(key: String?, value: Long) {
        val editor = preferences.edit()
        editor.putLong(key, value)
        editor.apply()
        editor.commit();
    }

    fun setData(key: String?, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
        editor.commit();
    }

    fun getDataString(key: String?): String? {
        return preferences.getString(key, null)
    }

    fun getDataInt(key: String?): Int {
        return preferences.getInt(key, 0)
    }

    fun getDataFloat(key: String?): Float {
        return preferences.getFloat(key, 0f)
    }

    fun getDataLong(key: String?): Long {
        return preferences.getLong(key, 0L)
    }

    fun getDataBool(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getDataBool(key: String?, The_default: Boolean): Boolean {
        return preferences.getBoolean(key, The_default)
    }

}
