package com.pdf.reader.data

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.lifecycle.liveData
import com.pdf.reader.model.Pdf
import kotlinx.coroutines.Dispatchers

class GetPdfFromURI(private val context: Context?,private val uri: Uri?) {

    fun getPdfList(): Pdf? {
        var pdfList: Pdf? = null
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.SIZE
        )
        val mimeType: String = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")!!
        val selectionArgs = arrayOf(mimeType)

        context?.contentResolver?.query(uri!!, projection, null, null, null)
            .use { cursor ->
                if (cursor != null) {
                    while (cursor.moveToFirst()) {
                        val columnId = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                        val columnAddDate =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
                        val columnModifiedDate =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
                        val columnName =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                        val columnTitle = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE)
                        val columnData = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                        val columnSize = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)


                         pdfList =   Pdf(
                                cursor.getLong(columnId),
                                cursor.getString(columnTitle),
                                cursor.getString(columnData),
                                cursor.getLong(columnAddDate),
                                cursor.getLong(columnModifiedDate),
                                cursor.getLong(columnSize),
                                System.currentTimeMillis()
                            )


                    }
                    cursor.close()
                }
            }

        return pdfList
    }
}