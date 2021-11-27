package com.pdf.reader.database

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.lifecycle.LiveData
import com.pdf.reader.model.Pdf

class PdfRepository(application: Application) {

    private var allPdf: LiveData<List<Pdf>>? = null
    private var bookmark: LiveData<List<Pdf>>? = null

    private val pdfDao = PdfDao?.getInstance(application)

    init {
        allPdf = pdfDao.getAllPdf()
        bookmark = pdfDao.getBookmark()
    }

    fun insert(result: Pdf?) {

        pdfDao.insert(result!!)

    }

    fun delete(result: Pdf?) {

        pdfDao.delete(result!!)

    }


    fun getAllPdf(): LiveData<List<Pdf>>? {
        return allPdf!!
    }

    fun getBookmark(): LiveData<List<Pdf>> {
        return bookmark!!
    }

    fun isBookmark(id: Long): Boolean{
        return pdfDao.isBookmark(id)
    }

    fun update(result: Pdf?) {

        pdfDao.update(result!!)

    }


}