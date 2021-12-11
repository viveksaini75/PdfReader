package com.pdf.reader.utils

import android.content.Context
import com.pdf.reader.R
import com.pdf.reader.preference.UserPreferences

fun getAppTheme(context: Context): Int {
    return when (UserPreferences(context).theme) {
        0 -> R.style.AppTheme
        1-> R.style.AppTheme_Dark
        else -> R.style.AppTheme
    }
}