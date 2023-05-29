package com.example.chatwiseassignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.example.chatwiseassignment.data.models.AlbumItem
import com.example.chatwiseassignment.databinding.RvListItemBinding

class AlbumAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<AlbumItem, AlbumAdapter.AlbumItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val binding = RvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class AlbumItemViewHolder(private val binding : RvListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(albumItem: AlbumItem) {
            binding.imageView.load(albumItem.url, imageLoader)
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<AlbumItem>() {

        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem) = oldItem == newItem
    }


}