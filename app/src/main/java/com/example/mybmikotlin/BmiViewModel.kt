package com.example.mybmikotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.data.InfoDao

class BmiViewModel(private val infoDao: InfoDao): ViewModel() {
    val normalList: LiveData<List<Info>> = infoDao.getNormalList().asLiveData()
    val thinList: LiveData<List<Info>> = infoDao.getThinList().asLiveData()
    val fatList: LiveData<List<Info>> = infoDao.getFatList().asLiveData()

    fun sendData(name:String, height: Double, weight: Double) {

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