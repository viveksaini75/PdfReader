package com.pdf.reader.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pdf.reader.R
import com.pdf.reader.activity.ViewPdfActivity
import com.pdf.reader.dialog.DetailsDialog
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.getDate
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import com.pdf.reader.utils.sharePdf
import com.pdf.reader.viewmodel.PdfViewModel
import java.nio.file.Files.delete

import java.util.*
import androidx.fragment.app.FragmentActivity
import com.pdf.reader.activity.MainActivity


class PdfAdapter(
    private val context: Context?,
    private val viewModel: PdfViewModel
) :
    ListAdapter<Pdf, PdfAdapter.ViewHolder>(ListAdapterCallBack), Filterable {

    private val resultsModel: MutableList<Pdf>? = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View? =
            LayoutInflater.from(parent.context).inflate(R.layout.pdf_list_item, parent, false)
        return ViewHolder(v!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pdfList = getItem(position)
        resultsModel?.addAll(listOf(pdfList))
        holder.title.text = pdfList.title
        holder.date.text = getDate(Date(pdfList.path?.getFile()?.lastModified()!!))
        holder.size.text = pdfList.size?.getFileSize()

        holder.card.setOnClickListener {
            //  viewModel.insert(pdfList)
            ViewPdfActivity.start(context, pdfList)

        }

        holder.popup?.setOnClickListener {
            val popupMenu = PopupMenu(
                context!!, it
            )
            popupMenu.inflate(R.menu.popupmenu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_share -> {
                        sharePdf(context, pdfList.path.getFile())
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

    override fun getItemCount(): Int {
        return currentList?.size!!
    }

    override fun submitList(list: List<Pdf>?) {
        super.submitList(list?.let { ArrayList(it) })
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var popup = itemView.findViewById<View>(R.id.popup) as ImageView
        var date = itemView.findViewById<View>(R.id.tvDate) as TextView
        var title = itemView.findViewById<View>(R.id.tvTitle) as TextView
        var size = itemView.findViewById<View>(R.id.tvSize) as TextView
        var card = itemView.findViewById<View>(R.id.cardLayout) as CardView
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
