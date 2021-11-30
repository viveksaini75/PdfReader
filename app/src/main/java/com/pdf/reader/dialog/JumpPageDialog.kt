package com.pdf.reader.dialog

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.pdf.reader.R
import com.pdf.reader.databinding.DetailsDialogBinding
import com.pdf.reader.databinding.FragmentMainBinding
import com.pdf.reader.databinding.LayoutJumpPageBinding
import com.pdf.reader.extension.Date.getDate
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.getFile
import com.pdf.reader.utils.getFileSize
import java.util.*

class JumpPageDialog : DialogFragment() {

    interface OnButtonClick {
        fun onButtonClick(page: Int?)
    }

    companion object {
        private const val PAGE_ID_KEY = "PAGE_ID_KEY"

        fun newInstance(page: Int?): JumpPageDialog {
            return JumpPageDialog().apply {
                arguments = Bundle().apply {
                    putInt(PAGE_ID_KEY, page!!)
                }
                isCancelable = true
            }
        }
    }

    private var page: Int? = null

    private var binding: LayoutJumpPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            page = it.getInt(PAGE_ID_KEY)
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

        binding = LayoutJumpPageBinding.inflate(LayoutInflater.from(context))


        return AlertDialog.Builder(requireActivity(), R.style.DialogAlertTheme)
            .setView(binding?.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvGo?.setOnClickListener {
            if ((binding?.editText?.text.toString()).isEmpty() || page!! < Integer.parseInt(binding?.editText?.text.toString())) {
                binding?.editText?.error = resources.getString(R.string.invalid_page)
            } else {
                if (activity is OnButtonClick) (activity as OnButtonClick).onButtonClick(
                    Integer.parseInt(binding?.editText?.text.toString())
                )
                dismiss()
            }
        }

        binding?.tvCancel?.setOnClickListener {
            dismiss()
        }

    }
}