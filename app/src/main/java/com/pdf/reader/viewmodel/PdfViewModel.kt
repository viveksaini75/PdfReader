package com.pdf.reader.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pdf.reader.data.GetAllPdf
import com.pdf.reader.data.GetPdfFromURI
import com.pdf.reader.database.PdfRepository
import com.pdf.reader.model.Pdf
import kotlinx.coroutines.launch

class PdfViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PdfRepository(application)

    private val allPdf = repository.getAllPdf()
    private val favourite = repository.getFavourite()


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

    fun getFavourite(): LiveData<List<Pdf>>? {
        return favourite!!
    }

    fun getAllPdf(): LiveData<List<Pdf>> {
        return GetAllPdf(getApplication()).getPdfList()
    }

    fun isFavourite(id: Long?): Boolean {
        return repository.isFavourite(id!!)
    }

    fun rememberPage(id: Long?): Int{
        return repository.rememberPage(id)
    }

    fun getPdfFromUri(string: String?): ArrayList<Pdf> {
        return GetPdfFromURI(getApplication(),string).getPdfList()
    }
}