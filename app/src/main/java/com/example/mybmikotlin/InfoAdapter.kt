package com.example.mybmikotlin

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.InfoItemViewBinding
import com.example.mybmikotlin.databinding.ModifyInfoDialogBinding
import java.text.DecimalFormat

class InfoAdapter(private val activity: AppCompatActivity, private val viewModel: BmiViewModel): RecyclerView.Adapter<InfoAdapter.ItemViewHolder>() {
    private var infoList= listOf<Info>()
    class ItemViewHolder(private val binding: InfoItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Info) {
            binding.apply {
                tvInfoName.text = data.name
                tvInfoHeight.text = data.getDoubleFormatText(data.height)
                tvInfoWeight.text = data.getDoubleFormatText(data.weight)
                tvInfoBmi.text = data.getDoubleFormatText(data.bmi)
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
        val itemData = infoList[position]
        holder.bind(itemData)
        holder.itemView.setOnClickListener { changeSelectBackground(it, viewModel.changeSelected(itemData.id)) }
//        holder.itemView.setOnLongClickListener {
//            val dialog = ModifyInfoDialogFragment(itemData, viewModel)
//            dialog.isCancelable = false
//            dialog.show(activity.supportFragmentManager, "modify")
//            true }
        changeSelectBackground(holder.itemView, viewModel.checkSelected(itemData.id))
    }

    private fun changeSelectBackground(view:View, selected: Boolean) {
        if (selected) {
            view.background = AppCompatResources.getDrawable(activity.baseContext, R.drawable.tv_border_selected)
        } else {
            view.background = AppCompatResources.getDrawable(activity.baseContext, R.drawable.tv_border)
        }
    }
}

class ModifyInfoDialogFragment(private val info: Info, private val viewModel: BmiViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = ModifyInfoDialogBinding.inflate(inflater)
            builder.setView(binding.root)
            bind(binding)
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return alertDialog
    }

    private fun bind(binding: ModifyInfoDialogBinding) {
        binding.apply {
            etModifyName.setText(info.name)
            etModifyHeight.setText(info.getDoubleFormatText(info.height))
            etModifyWeight.setText(info.getDoubleFormatText(info.weight))
            btModify.setOnClickListener {
                val check = checkValid(binding)
                if (check.first) {
                    viewModel.update(info.id, etModifyName.text.toString(), check.second, check.third)
                }
            }
            btCancel.setOnClickListener { dialog?.cancel() }
        }
    }

    private fun checkValid(binding: ModifyInfoDialogBinding): Triple<Boolean, Double, Double>{
        return if(checkEtEmpty(binding)) {
            showToast(getString(R.string.input_have_empty))
            Triple(false ,0.0, 0.0)
        } else {
            getCheckValue(binding)
        }
    }

    private fun checkEtEmpty(binding: ModifyInfoDialogBinding): Boolean {
        return binding.run {
            etModifyName.text.isBlank() || etModifyHeight.text.isBlank() || etModifyWeight.text.isBlank()
        }
    }

    private fun getCheckValue(binding: ModifyInfoDialogBinding): Triple<Boolean, Double, Double> {
        try {
            val height = binding.etModifyHeight.text.toString().toDouble()
            val weight = binding.etModifyWeight.text.toString().toDouble()

            if (height != 0.0 && weight != 0.0) {
                return Triple(true, height, weight)
            } else {
                showToast(getString(R.string.input_no_zero))
            }
        } catch (e: Exception) {
            showToast(getString(R.string.input_error))
        }
        return Triple(false, 0.0, 0.0)
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

}