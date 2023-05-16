package com.example.mybmikotlin

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.CategoryItemViewBinding

class CategoryAdapter(viewModel: BmiViewModel):
    ListAdapter<List<Info>, CategoryAdapter.ItemViewHolder>(DiffCallback) {
    class ItemViewHolder(private var binding: CategoryItemViewBinding):
        ViewHolder(binding.root) {
        fun bind(dataList: List<Info>) {
            binding.apply {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<List<Info>>() {
            override fun areItemsTheSame(oldItem: List<Info>, newItem: List<Info>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: List<Info>, newItem: List<Info>): Boolean {
                return oldItem == newItem
            }

        }
    }
}