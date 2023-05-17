package com.example.mybmikotlin

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.CategoryItemViewBinding

class CategoryAdapter(val context: Context, private val viewModel: BmiViewModel):
    ListAdapter<List<Info>, CategoryAdapter.ItemViewHolder>(DiffCallback) {
//    private val categoryList:List<CategoryData> by lazy { categoryListInit() }

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

//    private fun categoryListInit(): List<CategoryData> {
//        val categories = arrayOf(Category.THIN, Category.NORMAL, Category.FAT)
//        val titleTexts = arrayOf(context.getString(R.string.thin_format))
//    }

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

enum class Category {
    THIN, NORMAL, FAT
}

data class CategoryData(val titleText: String, val category: Category)