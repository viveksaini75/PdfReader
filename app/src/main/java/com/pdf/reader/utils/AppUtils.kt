package com.pdf.reader.utils

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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

fun getPath(context: Context?, uri: Uri?): String? {
    var filePath: String? = null
    val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        val cursor: Cursor? = context?.contentResolver?.query(uri!!, projection, null, null, null)
        if (cursor != null && cursor?.moveToFirst()) {

            val columnIndex: Int? = cursor?.getColumnIndexOrThrow(projection[0])
            filePath = cursor?.getString(columnIndex!!)
            cursor?.close()

    }
    return filePath

}

fun shareApp(context: Context) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Pdf reader ,Awesome app Please check out this app:\nhttps://play.google.com/store/apps/details?id=com.cobra.screenrecorder"
        )
        context?.startActivity(Intent.createChooser(intent, "Share..."))
    } catch (e: java.lang.Exception) {
    }
}

fun submitFeedback(context: Context) {
    try {
        var deviceInfo = "Device Info:"
        deviceInfo += """
 OS Version: ${System.getProperty("os.version")}(${Build.VERSION.INCREMENTAL})"""
        deviceInfo += """
 OS API Level: ${Build.VERSION.SDK_INT}"""
        deviceInfo += """
 Device: ${Build.DEVICE}"""
        deviceInfo += """
 Model (and Product): ${Build.MODEL} (${Build.PRODUCT})"""
        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", "piscreenrecorder.feedback@gmail.com", null)
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Device Info")
        emailIntent.putExtra(Intent.EXTRA_TEXT, deviceInfo)
        context?.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    } catch (e: java.lang.Exception) {
    }
}

fun gotoGooglePlay(context: Context, packageName: String?) {
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://play.google.com/store/apps/details?id=$packageName"
                )
            )
        )
    } catch (e: Exception) {
    }
}