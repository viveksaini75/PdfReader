package com.pdf.reader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pdf.reader.R
import com.pdf.reader.activity.MainActivity
import com.pdf.reader.activity.ViewPdfActivity
import com.pdf.reader.databinding.PdfListItemBinding
import com.pdf.reader.dialog.DetailsDialog
import com.pdf.reader.extension.Date.getCurrentDate
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import com.pdf.reader.utils.sharePdf
import com.pdf.reader.viewholder.BookmarkViewHolder
import com.pdf.reader.viewmodel.PdfViewModel

import java.util.*


class BookmarkAdapter(
    private val context: Context?,
    private val viewModel: PdfViewModel
) :
    ListAdapter<Pdf, BookmarkViewHolder>(ListAdapterCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
       val binding = PdfListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val pdfList = getItem(position)

        with(holder){
            with(pdfList){
                binding?.tvTitle?.text = title
                binding?.tvDate?.text =
                    getCurrentDate(Date(this?.time!!))
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

}
