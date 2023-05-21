package com.example.mybmikotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.InfoItemViewBinding

class InfoAdapter: RecyclerView.Adapter<InfoAdapter.ItemViewHolder>() {
    private var infoList= listOf<Info>()
    class ItemViewHolder(private val binding: InfoItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Info) {
            binding.apply {
                tvInfoName.text = data.name
                tvInfoHeight.text = data.height.toString()
                tvInfoWeight.text = data.weight.toString()
                tvInfoBmi.text = data.bmi.toString()
            }
        }
    }

    fun setData(data: List<Info>) {
        infoList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(InfoItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(infoList[position])
    }
}