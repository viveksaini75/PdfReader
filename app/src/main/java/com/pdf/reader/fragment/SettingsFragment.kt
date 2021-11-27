package com.pdf.reader.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdf.reader.R
import com.pdf.reader.databinding.FragmentSettingsBinding
import com.pdf.reader.preference.UserPreferences


class SettingsFragment : Fragment() {

    private val userPreferences by lazy {
        UserPreferences(requireContext())
    }
    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.scQuality?.isChecked = userPreferences.quality
        binding?.scQuality?.setOnCheckedChangeListener { buttonView, isChecked ->
            userPreferences?.quality = isChecked

        }
    }

}