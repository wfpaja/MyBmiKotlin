package com.example.mybmikotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.example.mybmikotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private val viewModel: MyBmiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        etInit()
        btnInit()
    }

    private fun etInit() {
        binding.apply {
            etName.addTextChangedListener(textWatcher)
            etHeight.addTextChangedListener(textWatcher)
            etWeight.addTextChangedListener(textWatcher)
        }
    }

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            setBtnBmiState()
        }
    }

    private fun btnInit() {
        binding.apply {
            btnBmi.setOnClickListener {  }
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

    private fun setBtnBmiState() {
        val check = binding.etName.length() > 0 && binding.etHeight.length() > 0 && binding.etWeight.length() > 0
        binding.btnBmi.isEnabled = check
    }

//    private fun checkInputValid(): Boolean {
//
//    }
}