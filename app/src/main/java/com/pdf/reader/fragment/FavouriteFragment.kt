package com.pdf.reader.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pdf.reader.adapter.FavouriteAdapter
import com.pdf.reader.databinding.FragmentFavouriteBinding
import com.pdf.reader.viewmodel.PdfViewModel


class FavouriteFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by lazy {
        ViewModelProvider(this)[PdfViewModel::class.java]
    }
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.recyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        getData()
    }

    private fun getData() {

        val adapter = FavouriteAdapter(requireContext(),viewModel)
        viewModel.getFavourite()?.observe(this) {
            if (it?.isEmpty()!!){
                binding.recyclerView?.visibility = View.GONE
                binding.messageLayout?.visibility = View.VISIBLE
            }else {
                binding.messageLayout?.visibility = View.GONE
                binding.recyclerView?.visibility = View.VISIBLE
                adapter.submitList(it)
            }
        }
        binding.recyclerView?.adapter = adapter
    }

    override fun onRefresh() {
        getData()
        binding.swipeRefreshLayout.isRefreshing = false
    }

}