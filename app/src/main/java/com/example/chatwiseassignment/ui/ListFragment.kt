package com.example.chatwiseassignment.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.example.chatwiseassignment.R
import com.example.chatwiseassignment.data.models.AlbumItem
import com.example.chatwiseassignment.databinding.FragmentHomeBinding
import com.example.chatwiseassignment.databinding.FragmentListBinding
import com.example.chatwiseassignment.ui.adapter.AlbumAdapter
import com.example.chatwiseassignment.util.NetworkResult
import com.example.chatwiseassignment.viewmodels.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<AlbumViewModel>()
    private val imageLoader by lazy { ImageLoader.Builder(requireContext()).placeholder(R.drawable.img_placeholder).build() }
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        fetchData()
    }

    private fun setupViews() {
        albumAdapter = AlbumAdapter(imageLoader)
        binding.rvImageList.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = albumAdapter
        }

        binding.fabRefresh.setOnClickListener {
            viewModel.getAlbum()
        }
    }

    private fun fetchData() {

        viewModel.response.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            binding.fabRefresh.visibility = View.GONE
            Log.d(TAG, "fetchData: data = ${it}")
            when(it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.fabRefresh.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    albumAdapter.submitList(it.data)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}