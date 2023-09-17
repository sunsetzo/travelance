package com.moneyminions.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.moneyminions.data.model.response.login.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object{
        private const val PREFERENCE_NAME = "travelance"
        private const val MEMBER_INFO = "member"
    }

    private fun getPreference(context: Context): SharedPreferences{
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
    private val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }
    private val gson = Gson()

    private fun putString(key: String, data: String?){
        editor.putString(key, data)
        editor.apply()
    }

    private fun getString(key: String, defValue: String? = null): String? {
        return prefs.getString(key, defValue)
    }

}