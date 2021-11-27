package com.pdf.reader.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationBarView
import com.pdf.reader.R
import com.pdf.reader.adapter.PdfAdapter
import com.pdf.reader.databinding.ActivityMainBinding
import com.pdf.reader.fragment.BookmarkFragment
import com.pdf.reader.fragment.MainFragment
import com.pdf.reader.fragment.RecentFragment
import com.pdf.reader.model.Pdf
import com.pdf.reader.utils.loadFragment
import com.pdf.reader.viewmodel.PdfViewModel

class MainActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tool.toolBar)

        binding.bottomBar.setOnItemSelectedListener(this)
        loadMainFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == binding.bottomBar.selectedItemId) {
            return false
        }
        loadFragment(item.itemId)
        return true
    }

    private fun loadFragment(itemId: Int) {
        when (itemId) {
            R.id.menu_home -> {
                loadMainFragment()
                binding.tool.toolBar.title = getString(R.string.app_name)
            }
            R.id.menu_recent -> {
                RecentFragment().loadFragment(this, R.id.container)
                binding.tool.toolBar.title = getString(R.string.recent)

            }
            R.id.menu_bookmark -> {
                BookmarkFragment().loadFragment(this, R.id.container)
                binding.tool.toolBar.title = getString(R.string.bookmark)

            }
            R.id.menu_settings -> {
                //SettingsFragment().loadFragment(this, R.id.container)
                binding.tool.toolBar.title = getString(R.string.settings)

            }
        }
    }

    private fun loadMainFragment() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            MainFragment().loadFragment(this, R.id.container)
        } else {
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}