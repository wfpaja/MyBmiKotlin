package com.example.mybmikotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybmikotlin.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: BmiViewModel by viewModels {
        BmiViewModelFactory(
            (application as BmiApplication).database.infoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnInit()
        rvInit()
        viewModel.selectedCount.observe(this) {
            count -> count.run {
                binding.btnDeleteBySelected.isEnabled = count > 0
                binding.tvSelectedCount.text = String.format(getString(R.string.selected_format), count)
            }

        }
        viewModel.bmiResult.observe(this) {
            bmi -> setTvBmi(bmi)
        }
    }

    private fun btnInit() {
        binding.apply {
            btnBmi.setOnClickListener { checkValidAndSend() }
            btnClear.setOnClickListener {
                etHeight.setText("")
                etName.setText("")
                etWeight.setText("")
                tvBmi.text = ""
            }
            btnDeleteAll.setOnClickListener { showConfirmDialog(true) }
            btnDeleteBySelected.setOnClickListener { showConfirmDialog(false) }
        }
    }

    private fun rvInit() {
        val adapter = CategoryAdapter(this, viewModel)
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.thinList.observe(this) {
            items -> adapter.update(Category.THIN, items)
        }
        viewModel.normalList.observe(this) {
            items -> adapter.update(Category.NORMAL, items)
        }
        viewModel.fatList.observe(this) {
            items -> adapter.update(Category.FAT, items)
        }
    }

    private fun checkValidAndSend(){
        if(checkEtEmpty()) {
            showToast(getString(R.string.input_have_empty))
        } else {
            val (ok, height, weight) = getCheckValue()
            if (ok) {
                viewModel.sendData(binding.etName.text.toString(), height, weight)
            }
        }
    }

    private fun checkEtEmpty(): Boolean {
        return binding.run {
            etName.text.isBlank() || etHeight.text.isBlank() || etWeight.text.isBlank()
        }
    }

    private fun getCheckValue(): Triple<Boolean, Double, Double> {
        try {
            val height = binding.etHeight.text.toString().toDouble()
            val weight = binding.etWeight.text.toString().toDouble()

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
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setTvBmi(bmi: Double) {
        val df = DecimalFormat("0.##")
        binding.tvBmi.text = df.format(bmi)
    }

    private fun showConfirmDialog(isAll: Boolean) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.attention_text))
            .setMessage(
                if(isAll) getString(R.string.delete_all_question)
                else getString(R.string.delete_by_id_question)
            )
            .setCancelable(false)
            .setNegativeButton(getString(R.string.cancel_text)) {dialog, _ -> dialog.cancel()}
            .setPositiveButton(getString(R.string.confirm)) {_, _ -> if (isAll) viewModel.deleteAll() else viewModel.deleteByIds()}
            .show()
    }
}