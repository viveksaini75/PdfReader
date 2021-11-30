package com.pdf.reader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pdf.reader.activity.ViewPdfActivity
import com.pdf.reader.databinding.PdfListItemBinding
import com.pdf.reader.databinding.RecentListItemBinding
import com.pdf.reader.extension.Date.getCurrentDate
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import com.pdf.reader.viewholder.RecentViewHolder
import com.pdf.reader.viewmodel.PdfViewModel

import java.util.*


class RecentAdapter(
    private val context: Context?,
    private val viewModel: PdfViewModel
) :
    ListAdapter<Pdf, RecentViewHolder>(ListAdapterCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
       val binding = RecentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val pdfList = getItem(position)

        with(holder) {
            with(pdfList) {
                binding?.tvTitle?.text = title
                binding?.tvDate?.text = getCurrentDate(Date(this?.time!!))
                binding?.tvSize?.text = size?.getFileSize()
                binding?.cardLayout?.setOnClickListener {
                    ViewPdfActivity.start(context, this)
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
