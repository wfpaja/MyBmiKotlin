package com.example.mybmikotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mybmikotlin.data.Info
import com.example.mybmikotlin.data.InfoDao
import kotlinx.coroutines.launch
import kotlin.math.pow

class BmiViewModel(private val infoDao: InfoDao): ViewModel() {
    val normalList: LiveData<List<Info>> = infoDao.getNormalList().asLiveData().distinctUntilChanged()
    val thinList: LiveData<List<Info>> = infoDao.getThinList().asLiveData().distinctUntilChanged()
    val fatList: LiveData<List<Info>> = infoDao.getFatList().asLiveData().distinctUntilChanged()
    private val selectedSet = hashSetOf<Long>()
    val selectedCount = MutableLiveData(0)
    val bmiResult = MutableLiveData(0.0)

    fun sendData(name:String, height: Double, weight: Double) {
        val info = createInfo(null, name, height, weight)
        viewModelScope.launch {
            infoDao.insert(info)
        }
        bmiResult.value = info.bmi
    }

    private fun createInfo(id:Long?, name:String, height: Double, weight: Double): Info {
        val bmiId = id ?: System.currentTimeMillis()
        val bmi = weight / (height / 100).pow(2)
        return Info(bmiId, name, height, weight, bmi)
    }

    fun update(id: Long, name: String, height: Double, weight: Double) {
        val info = createInfo(id, name, height, weight)
        viewModelScope.launch {
            infoDao.update(info)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            infoDao.deleteAll()
        }
    }

    fun changeSelected(id: Long): Boolean {
        var result = true
        if (selectedSet.contains(id)) {
            selectedSet.remove(id)
            result = false
        } else {
            selectedSet.add(id)
        }
        selectedCount.value = selectedSet.size
        return result
    }

    fun checkSelected(id: Long) = selectedSet.contains(id)

    fun deleteByIds() {
        val ids = selectedSet.toList()
        viewModelScope.launch {
            infoDao.deleteByIds(ids)
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