package com.example.mybmikotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.databinding.ActivityMainBinding
import java.lang.Exception


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
            btnDeleteAll.setOnClickListener {  }
            btnDeleteBySelected.setOnClickListener {  }
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
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }
}