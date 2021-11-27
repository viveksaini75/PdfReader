package com.pdf.reader.adapter

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
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import com.pdf.reader.utils.sharePdf
import com.pdf.reader.viewmodel.PdfViewModel

import java.util.*
import com.pdf.reader.activity.MainActivity
import com.pdf.reader.databinding.PdfListItemBinding
import com.pdf.reader.extension.Date.getDate


class PdfAdapter(
    private val context: Context?,
    private val viewModel: PdfViewModel
) :
    ListAdapter<Pdf, PdfAdapter.ViewHolder>(ListAdapterCallBack), Filterable {

    private val resultsModel: MutableList<Pdf>? = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = PdfListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pdfList = getItem(position)
        resultsModel?.addAll(listOf(pdfList))

        with(holder){
            with(pdfList){
                binding?.tvTitle?.text = title
                binding?.tvDate?.text = getDate(Date(path?.getFile()?.lastModified()!!))
                binding?.tvSize?.text = size?.getFileSize()
                binding?.cardLayout?.setOnClickListener {
                    ViewPdfActivity.start(context, this)
                }
                binding?.popup?.setOnClickListener {
                    val popupMenu = PopupMenu(
                        context!!, it
                    )
                    popupMenu.inflate(R.menu.popupmenu)
                    popupMenu.show()

                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menu_share -> {
                                sharePdf(context, pdfList.path?.getFile())
                            }

                            R.id.menu_detail -> {
                                val activity = context as MainActivity
                                DetailsDialog.newInstance(pdfList).show(activity.supportFragmentManager,"")
                            }
                        }
                        true
                    }
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

    class ViewHolder(private val pdfListItemBinding: PdfListItemBinding?) : RecyclerView.ViewHolder(
        pdfListItemBinding?.root!!
    ){
        val binding = pdfListItemBinding
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
}
