package com.pdf.reader.utils

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.pdf.reader.R
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val PDF_INTENT = "pdf_intent"

fun Fragment.loadFragment(activity: AppCompatActivity, layoutId: Int) {
    val fm: FragmentManager = activity?.supportFragmentManager
    val ft = fm.beginTransaction()
    ft.replace(layoutId, this)
    ft.commitAllowingStateLoss()
}

fun getDate(date: Date?): String? {
    val formatter = SimpleDateFormat("dd-mm-yyyy HH:mm")
    return formatter.format(date)
}

fun String.getFile(): File? {
    try {
        return if (!TextUtils.isEmpty(this)) {
            File(this)
        } else {
            null
        }
    } catch (ignored: Exception) {
    }
    return null
}


fun Long.getFileSize(): String? {
    val modifiedFileSize: String?
    val fileSize: Double?
    if (this > 0) {
        fileSize = this.toDouble()
        modifiedFileSize = if (fileSize!! < 1024) {
            "$fileSize B"
        } else if (fileSize > 1024 && fileSize < 1024 * 1024) {
            ((fileSize / 1024 * 100.0).roundToInt() / 100.0).toString() + " KB"
        } else {
            ((fileSize / (1024 * 1204) * 100.0).roundToInt() / 100.0).toString() + " MB"
        }
    } else {
        modifiedFileSize = "<Unknown>"
    }
    return modifiedFileSize
}

fun sharePdf(context: Context?, file: File?) {
    try {
        val fileUri = FileProvider.getUriForFile(
            context!!, context.packageName + ".provider",
            file!!
        )
        val shareIntent = Intent()
            .setAction(Intent.ACTION_SEND)
            .setType("application/pdf")
            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .putExtra(Intent.EXTRA_STREAM, fileUri)
        context?.startActivity(
            Intent.createChooser(
                shareIntent,
                context?.getString(R.string.share_intent_title)
            )
        )
    } catch (e: Exception) {
    }
}
