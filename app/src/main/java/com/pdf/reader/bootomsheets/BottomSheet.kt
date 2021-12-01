package com.pdf.reader.bootomsheets

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pdf.reader.R
import com.pdf.reader.databinding.BottomSheetDialogBinding
import com.pdf.reader.interfaces.BottomSheetInterface
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.BS_DETAIL_KEY
import com.pdf.reader.utils.BS_SHARE_KEY
import com.pdf.reader.viewmodel.PdfViewModel


fun Activity.showBottomSheet(listener: BottomSheetInterface, pdf: Pdf?, viewModel: PdfViewModel?) {

    var binding: BottomSheetDialogBinding? = null
    binding = BottomSheetDialogBinding.inflate(layoutInflater)
    val dialog: BottomSheetDialog = createBottomSheet()
    dialog.setContentView(binding.root)
    dialog.show()

    pdf?.isFavourite = viewModel?.isFavourite(pdf?.id)!!
    if (viewModel.isFavourite(pdf?.id)) {
        binding.imageFavourite.setImageResource(R.drawable.ic_favourite_black_24dp)
        binding.tvFavourite.text = getString(R.string.un_favourite)
    } else {
        binding.imageFavourite.setImageResource(R.drawable.ic_favourite_border_black_24dp)
        binding.tvFavourite.text = getString(R.string.favourite)
    }

    binding?.detailLayout?.setOnClickListener {
        listener.onItemClick(BS_DETAIL_KEY)
        dialog.dismiss()
    }

    binding?.shareLayout?.setOnClickListener {
        listener?.onItemClick(BS_SHARE_KEY)
        dialog.dismiss()
    }

    binding.favouriteLayout?.setOnClickListener {
        pdf?.isFavourite = !viewModel?.isFavourite(pdf?.id)!!
        viewModel.insert(pdf)
        if (viewModel.isFavourite(pdf?.id)) {
            binding.imageFavourite.setImageResource(R.drawable.ic_favourite_black_24dp)
            binding.tvFavourite.text = getString(R.string.un_favourite)
        } else {
            binding.imageFavourite.setImageResource(R.drawable.ic_favourite_border_black_24dp)
            binding.tvFavourite.text = getString(R.string.favourite)
        }
        dialog.dismiss()
    }

}


fun Activity.createBottomSheet(): BottomSheetDialog {
    return BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
}