package com.kotlin.allapprxjava.unit

import android.content.Context
import android.content.SharedPreferences

open class PrefUtils {
    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
    }

    fun storeApiKey(context: Context, apiKey: String) {
        var edit = getSharedPreference(context).edit()
        edit.putString("API_KEY", apiKey)
        edit.commit()
    }

    fun getApiKey(cont: Context): String? {
        return getSharedPreference(cont).getString("API_KEY", null)
    }
}