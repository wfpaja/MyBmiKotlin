package com.example.mybmikotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.InfoItemViewBinding
import java.text.DecimalFormat

class InfoAdapter(private val context: Context, private val viewModel: BmiViewModel): RecyclerView.Adapter<InfoAdapter.ItemViewHolder>() {
    private var infoList= listOf<Info>()
    class ItemViewHolder(private val binding: InfoItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Info) {
            binding.apply {
                tvInfoName.text = data.name
                tvInfoHeight.text = getFormatText(data.height)
                tvInfoWeight.text = getFormatText(data.weight)
                tvInfoBmi.text = getFormatText(data.bmi)
            }
        }
        private fun getFormatText(bmi: Double): String = DecimalFormat("0.##").format(bmi)
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
        val itemData = infoList[position]
        holder.bind(itemData)
        holder.itemView.setOnClickListener { changeSelectBackground(it, viewModel.changeSelected(itemData.id)) }
        holder.itemView.setOnLongClickListener { true }
        changeSelectBackground(holder.itemView, viewModel.checkSelected(itemData.id))
    }

    private fun changeSelectBackground(view:View, selected: Boolean) {
        if (selected) {
            view.background = AppCompatResources.getDrawable(context, R.drawable.tv_border_selected)
        } else {
            view.background = AppCompatResources.getDrawable(context, R.drawable.tv_border)
        }
    }
}