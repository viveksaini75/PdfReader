package com.pdf.reader.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdf.reader.data.GetAllPdf
import com.pdf.reader.data.GetPdfFromURI
import com.pdf.reader.database.PdfRepository
import com.pdf.reader.model.Pdf
import kotlinx.coroutines.launch

class PdfViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PdfRepository(application)

    private val allPdf = repository.getAllPdf()
    private val bookmark = repository.getBookmark()


    fun insert(pdf: Pdf?) {
        viewModelScope.launch {
            repository.insert(pdf)
        }
    }


    fun update(pdf: Pdf?) {
        viewModelScope.launch {
            repository.update(pdf)
        }
    }

    fun delete(pdf: Pdf?) {
        viewModelScope.launch {
            repository.delete(pdf)
        }
    }


    fun getAllPdfDatabase(): LiveData<List<Pdf>>? {
        return allPdf!!
    }

    fun getBookmark(): LiveData<List<Pdf>>? {
        return bookmark!!
    }

    fun getAllPdf(): LiveData<List<Pdf>> {
        return GetAllPdf(getApplication()).getPdfList()
    }

    fun isBookmark(id: Long?): Boolean {
        return repository.isBookmark(id!!)
    }

    fun getPdfFromUri(uri: Uri?): Pdf? {
        return GetPdfFromURI(getApplication(),uri).getPdfList()
    }
}