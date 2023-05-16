package com.example.mybmikotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.data.InfoDao
import kotlinx.coroutines.launch
import kotlin.math.pow

class BmiViewModel(private val infoDao: InfoDao): ViewModel() {
    val normalList: LiveData<List<Info>> = infoDao.getNormalList().asLiveData()
    val thinList: LiveData<List<Info>> = infoDao.getThinList().asLiveData()
    val fatList: LiveData<List<Info>> = infoDao.getFatList().asLiveData()

    fun sendData(name:String, height: Double, weight: Double) {
        val id = System.currentTimeMillis()
        val bmi = weight / (height / 100).pow(2)
        val info = Info(id, name, height, weight, bmi)
        viewModelScope.launch {
            infoDao.insert(info)
        }
    }

}

class BmiViewModelFactory(private val infoDao: InfoDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(BmiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BmiViewModel(infoDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}