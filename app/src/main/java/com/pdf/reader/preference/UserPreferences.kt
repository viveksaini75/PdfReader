package com.pdf.reader.preference

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private var preferences: SharedPreferences? = null

    init {
        preferences = context.getSharedPreferences(MY_PREFS_NAME, 0)
    }

    fun edit(): SharedPreferences.Editor? {
        return preferences?.edit()
    }


    var doNotAskAgain by preferences?.booleanPreference(key_do_not_ask_again, false)!!
    var quality by preferences?.booleanPreference(key_do_quality, false)!!
    var animation by preferences?.booleanPreference(key_do_animation, true)!!
    var rememberPage by preferences?.booleanPreference(key_do_remember_page, true)!!

}


const val MY_PREFS_NAME = "pref_pdf_reader"

const val key_do_not_ask_again = "key_do_not_ask_again"
const val key_do_quality = "key_do_quality"
const val key_do_animation = "key_do_animation"
const val key_do_remember_page = "key_do_remember_page"

