package com.pdf.reader.bootomsheets

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pdf.reader.R
import com.pdf.reader.databinding.BottomSheetDialogBinding
import com.pdf.reader.interfaces.BottomSheetInterface
import com.pdf.reader.utils.BS_DETAIL_KEY
import com.pdf.reader.utils.BS_SHARE_KEY


fun Activity.showBottomSheet(listener: BottomSheetInterface) {

    var binding: BottomSheetDialogBinding? = null
    binding = BottomSheetDialogBinding.inflate(layoutInflater)
    val dialog: BottomSheetDialog = createBottomSheet()
    dialog.setContentView(binding.root)
    dialog.show()

    binding?.detailLayout?.setOnClickListener {
        listener.onItemClick(BS_DETAIL_KEY)
        dialog.dismiss()
    }

    binding?.shareLayout?.setOnClickListener {
        listener?.onItemClick(BS_SHARE_KEY)
        dialog.dismiss()
    }

}


fun Activity.createBottomSheet(): BottomSheetDialog {
    return BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
}