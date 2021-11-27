package com.pdf.reader.preference

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private  var preferences: SharedPreferences?=null
     init {
        preferences= context.getSharedPreferences(MY_PREFS_NAME, 0)
     }
    fun edit(): SharedPreferences.Editor? {
       return preferences?.edit()
    }


       var doNotAskAgain by preferences?.booleanPreference(key_do_not_ask_again,false)!!

}



const val MY_PREFS_NAME="pref_pdf_reader"

const val key_do_not_ask_again = "key_do_not_ask_again"

