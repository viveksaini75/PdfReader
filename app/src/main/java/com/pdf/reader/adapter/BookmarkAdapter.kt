package com.pdf.reader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pdf.reader.R
import com.pdf.reader.activity.MainActivity
import com.pdf.reader.activity.ViewPdfActivity
import com.pdf.reader.dialog.DetailsDialog
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.getDate
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import com.pdf.reader.utils.sharePdf
import com.pdf.reader.viewmodel.PdfViewModel

import java.util.*


class BookmarkAdapter(
    private val context: Context?,
    private val viewModel: PdfViewModel
) :
    ListAdapter<Pdf, BookmarkAdapter.ViewHolder>(ListAdapterCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View? =
            LayoutInflater.from(parent.context).inflate(R.layout.pdf_list_item, parent, false)
        return ViewHolder(v!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pdfList = getItem(position)

        holder.title.text = pdfList.title
        holder.date.text = com.pdf.reader.extension.Date.getCurrentDate(Date(pdfList?.time!!))
        holder.size.text = pdfList.size?.getFileSize()

        holder.card.setOnClickListener {
           // viewModel.insert(pdfList)
            ViewPdfActivity.start(context,pdfList)

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
}
