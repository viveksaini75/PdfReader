package com.pdf.reader.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdf.reader.BuildConfig
import com.pdf.reader.R
import com.pdf.reader.activity.MainActivity
import com.pdf.reader.databinding.FragmentSettingsBinding
import com.pdf.reader.preference.UserPreferences
import com.pdf.reader.utils.gotoGooglePlay
import com.pdf.reader.utils.shareApp
import com.pdf.reader.utils.submitFeedback


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

        when(userPreferences.theme){
            0 ->  {
                binding?.scDarkMode?.isChecked = false
                binding?.tvDrakMode?.text = getString(R.string.off)
            }
            1 -> {
                binding?.scDarkMode?.isChecked = true
                binding?.tvDrakMode?.text = getString(R.string.on)
            }
        }
        binding?.scDarkMode?.setOnCheckedChangeListener{_,isChecked ->
            if (isChecked){
                userPreferences.theme = 1
                binding?.tvDrakMode?.text = getString(R.string.on)
            }else {
                userPreferences.theme = 0
                binding?.tvDrakMode?.text = getString(R.string.off)
            }
            restartMainActivity()
        }

        binding?.scQuality?.isChecked = userPreferences.quality
        binding?.scQuality?.setOnCheckedChangeListener { buttonView, isChecked ->
            userPreferences?.quality = isChecked

        }
        binding?.scAnimation?.isChecked = userPreferences.animation
        binding?.scAnimation?.setOnCheckedChangeListener { buttonView, isChecked ->
            userPreferences?.animation = isChecked

        }
        binding?.scRememberPage?.isChecked = userPreferences.rememberPage
        binding?.scRememberPage?.setOnCheckedChangeListener { buttonView, isChecked ->
            userPreferences?.rememberPage = isChecked

        }

        binding?.feedbackLayout?.setOnClickListener {
            submitFeedback(requireContext())
        }

        binding?.shareLayout?.setOnClickListener {
            shareApp(requireContext())
        }

        binding?.rateUsLayout?.setOnClickListener {
            gotoGooglePlay(requireContext(),requireContext().packageName)

        }

        binding?.checkUpdateLayout?.setOnClickListener {
            gotoGooglePlay(requireContext(),requireContext().packageName)
        }

        binding?.tvVersion?.text = "Version : ${BuildConfig.VERSION_NAME}"
    }

    private fun restartMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}