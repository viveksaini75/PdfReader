package com.pdf.reader.dialog

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.pdf.reader.R
import com.pdf.reader.databinding.DetailsDialogBinding
import com.pdf.reader.databinding.FragmentMainBinding
import com.pdf.reader.extension.Date.getDate
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import java.util.*

class DetailsDialog: DialogFragment() {
    companion object {
        private const val PDF_ID_KEY = "PDF_ID_KEY"

        fun newInstance(pdf: Pdf?): DetailsDialog {
            return DetailsDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(PDF_ID_KEY, pdf)
                }
                isCancelable = true
            }
        }
    }

    private var pdf: Pdf? = null
    private var _binding: DetailsDialogBinding? = null

    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pdf = it.getSerializable(PDF_ID_KEY) as Pdf?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DetailsDialogBinding.inflate(LayoutInflater.from(context))


        return AlertDialog.Builder(requireActivity(), R.style.DialogAlertTheme)
            .setView(binding?.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                dismiss()
            }
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvTitle?.text = pdf?.title
        binding?.tvDate?.text = getDate(Date(pdf?.path?.getFile()?.lastModified()!!))
        binding?.tvSize?.text = pdf?.size?.getFileSize()
        binding?.tvPath?.text = pdf?.path
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}