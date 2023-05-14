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
    private val viewModel: BmiViewModel by viewModels()

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

    private fun checkValidAndSend(){
        if(checkEtEmpty()) {
            Toast.makeText(this@MainActivity, resources.getText(R.string.input_have_empty), Toast.LENGTH_SHORT).show()
        } else {
//            checkValue()
        }

    }

    private fun checkEtEmpty(): Boolean {
        return binding.run {
            etName.text.isBlank() || etHeight.text.isBlank() || etWeight.text.isBlank()
        }
    }

//    private fun checkValue(): Boolean {
//        binding.apply {
//            val result = false
//            try {
//                val height = etHeight.text.toString().toDouble()
//                val weight = etWeight.text.toString().toDouble()
//
//                if (height != 0.0 || weight != 0.0) {
//                    viewModel.sendData(etName.text.toString(), height, weight)
//                } else {
//                    Toast.makeText(this@MainActivity, resources.getText(R.string.input_no_zero), Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                Toast.makeText(this@MainActivity, resources.getText(R.string.input_error), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun changeToInfo() {

    }
}