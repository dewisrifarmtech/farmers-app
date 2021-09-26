package com.dewisri.smartfarming.utils

import android.content.Context
import androidx.core.content.edit

class IntroPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "myPrefs"
        private const val IS_INTRO_OPENED = "isIntroOpened"
    }
    private val pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun savePrefsData(boolean: Boolean){
        pref.edit {
            putBoolean("isIntroOpened", boolean)
        }
    }

    fun restorePrefData(): Boolean {
        return pref.getBoolean("isIntroOpened", true)
    }


}