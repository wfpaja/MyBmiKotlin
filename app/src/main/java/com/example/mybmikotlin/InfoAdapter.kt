package com.example.mybmikotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.data.getDoubleFormatText
import com.example.mybmikotlin.databinding.InfoItemViewBinding

class InfoAdapter(private val activity: AppCompatActivity, private val viewModel: BmiViewModel):
    ListAdapter<Info, InfoAdapter.ItemViewHolder>(DiffCallback) {
    class ItemViewHolder(private var binding: InfoItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: Info) {
            binding.apply {
                tvInfoName.text = info.name
                tvInfoHeight.text = info.getDoubleFormatText(info.height)
                tvInfoWeight.text = info.getDoubleFormatText(info.weight)
                tvInfoBmi.text = info.getDoubleFormatText(info.bmi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(InfoItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemData = getItem(position)
        holder.bind(itemData)
        holder.itemView.setOnClickListener { changeSelectBackground(it, viewModel.changeSelected(itemData.id)) }
        holder.itemView.setOnLongClickListener {
            val dialog = ModifyInfoDialogFragment(itemData, viewModel)
            dialog.isCancelable = false
            dialog.show(activity.supportFragmentManager, "modify")
            true }
        changeSelectBackground(holder.itemView, viewModel.checkSelected(itemData.id))
    }

    private fun changeSelectBackground(view:View, selected: Boolean) {
        if (selected) {
            view.background = AppCompatResources.getDrawable(activity, R.drawable.tv_border_selected)
        } else {
            view.background = AppCompatResources.getDrawable(activity, R.drawable.tv_border)
        }
    }

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<Info>() {
            override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean {
                return oldItem == newItem
            }
        }
    }
}