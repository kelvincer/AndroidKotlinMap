package com.home.androidkotlinmapac

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.home.androidkotlinmapac.entities.Result

class MainViewModel : ViewModel() {
    private val repository = MainModel()
    private var dataResult: MutableLiveData<List<Result>>? = null

    init {
        dataResult = MutableLiveData()
        repository.getResult().observeForever { results -> dataResult?.setValue(results) }
    }


    internal fun getPlaces() {
        repository.getData()
    }

    internal fun getDataResult(): LiveData<List<Result>>? {
        return dataResult
    }

}