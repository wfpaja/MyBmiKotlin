package com.example.mybmikotlin

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.CategoryItemViewBinding

class CategoryAdapter(private val activity: Activity, private val viewModel: BmiViewModel):
    RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {
    private val categoryList = categoryListInit()


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

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    private fun categoryListInit(): MutableList<CategoryData> {
        val categories = arrayOf(Category.THIN, Category.NORMAL, Category.FAT)
        val titleTexts = arrayOf(
            activity.getString(R.string.thin_format),
            activity.getString(R.string.normal_format),
            activity.getString(R.string.fat_format)
        )
        val result = mutableListOf<CategoryData>()
        for (i in categories.indices) {
            val item = CategoryData(categories[i], titleTexts[i], mutableListOf<Info>())
            result.add(item)
        }
        return result
    }

    fun update(category: Category, infoList: List<Info>) {
        for (i in categoryList.indices){
            if (categoryList[i].category == category) {
                categoryList[i].infoList = infoList
                notifyItemChanged(i)
                break
            }
        }
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

enum class Category {
    THIN, NORMAL, FAT
}

data class CategoryData(val category: Category, val titleText: String, var infoList: List<Info>)