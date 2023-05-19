package com.example.mybmikotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.CategoryItemViewBinding

class CategoryAdapter(private val context: Context):
    RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {
    private val categoryList = categoryListInit()


    class ItemViewHolder(private var binding: CategoryItemViewBinding):
        ViewHolder(binding.root) {
        fun bind(data: CategoryData) {
            binding.apply {
                tvCategory.text = String.format(data.titleText, data.infoList.size)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    private fun categoryListInit(): MutableList<CategoryData> {
        val categories = arrayOf(Category.THIN, Category.NORMAL, Category.FAT)
        val titleTexts = arrayOf(
            context.getString(R.string.thin_format),
            context.getString(R.string.normal_format),
            context.getString(R.string.fat_format)
        )

        val result = mutableListOf<CategoryData>()
        for (i in categories.indices) {
            val item = CategoryData(categories[i], titleTexts[i], mutableListOf())
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
}

enum class Category {
    THIN, NORMAL, FAT
}

data class CategoryData(val category: Category, val titleText: String, var infoList: List<Info>)