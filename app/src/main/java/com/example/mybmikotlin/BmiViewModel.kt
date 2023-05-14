package com.example.mybmikotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.data.InfoDao

class BmiViewModel(private val infoDao: InfoDao): ViewModel() {
    val normalList: LiveData<List<Info>> = infoDao.getNormalList().asLiveData()
    val thinList: LiveData<List<Info>> = infoDao.getThinList().asLiveData()
    val fatList: LiveData<List<Info>> = infoDao.getFatList().asLiveData()

    fun sendData(name:String, height: Double, weight: Double) {

    }
}