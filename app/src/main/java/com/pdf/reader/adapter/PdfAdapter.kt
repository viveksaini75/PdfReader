package com.pdf.reader.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pdf.reader.R
import com.pdf.reader.activity.ViewPdfActivity
import com.pdf.reader.dialog.DetailsDialog
import com.pdf.reader.model.Pdf
import com.pdf.reader.viewmodel.PdfViewModel

import java.util.*
import com.pdf.reader.activity.MainActivity
import com.pdf.reader.bootomsheets.showBottomSheet
import com.pdf.reader.databinding.PdfListItemBinding
import com.pdf.reader.extension.Date.getDate
import com.pdf.reader.interfaces.BottomSheetInterface
import com.pdf.reader.utils.*
import com.pdf.reader.viewholder.PdfViewHolder


class PdfAdapter(
    private val activity: Activity?,
    private val viewModel: PdfViewModel
) :
    ListAdapter<Pdf, PdfViewHolder>(ListAdapterCallBack), Filterable,BottomSheetInterface {

    private var pdfList: Pdf? = null
    private val resultsModel: MutableList<Pdf>? = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
       val binding = PdfListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PdfViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        pdfList = getItem(position)
        resultsModel?.addAll(listOf(getItem(position)))

        with(holder){
            with(getItem(position)){
                binding?.tvTitle?.text = title
                binding?.tvDate?.text = getDate(Date(path?.getFile()?.lastModified()!!))
                binding?.tvSize?.text = size?.getFileSize()
                binding?.cardLayout?.setOnClickListener {
                    ViewPdfActivity.start(activity, this)
                }
                binding?.popup?.setOnClickListener {
                    activity?.showBottomSheet(this@PdfAdapter)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return currentList?.size!!
    }

    override fun submitList(list: List<Pdf>?) {
        super.submitList(list?.let { ArrayList(it) })
    }



    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val filterResults = FilterResults()
                if (constraint?.isEmpty()!!) {
                    filterResults.count = resultsModel?.size!!
                    filterResults.values = resultsModel
                } else {
                    val resultsModel: MutableList<Pdf>? = ArrayList()
                    val searchStr = constraint.toString().lowercase()
                    val searchStr1 = constraint.toString().uppercase()
                    for (itemsModel in currentList) {
                        if (itemsModel.title.contains(searchStr) || itemsModel.title.contains(
                                searchStr1
                            )
                        ) {
                            resultsModel?.add(itemsModel)
                        }
                        filterResults.count = resultsModel?.size!!
                        filterResults.values = resultsModel
                    }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults?) {
                submitList(results?.values as List<Pdf>?)
            }
        }
    }

    override fun onItemClick(string: String?) {
        when(string){
            BS_DETAIL_KEY->{
                val activity = activity as MainActivity
                DetailsDialog.newInstance(pdfList).show(activity.supportFragmentManager,"")
            }

            BS_SHARE_KEY->{
                sharePdf(activity, pdfList?.path?.getFile())
            }
        }
    }
}
