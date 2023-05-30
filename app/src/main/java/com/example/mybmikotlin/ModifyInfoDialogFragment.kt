package com.example.mybmikotlin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.ModifyInfoDialogBinding

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
                    dialog?.cancel()
                    showToast(getString(R.string.modify_success))
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
                showToast(getString(R.string.input_condition))
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