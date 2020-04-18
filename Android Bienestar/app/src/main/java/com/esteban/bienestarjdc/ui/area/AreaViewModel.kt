package com.esteban.bienestarjdc.ui.area

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.network.ApiException
import com.esteban.bienestarjdc.network.ApiSuccess
import com.esteban.bienestarjdc.repository.AreaRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AreaViewModel (private val areaRepository: AreaRepository) : ViewModel() {

    private val areasMLD = MutableLiveData<List<Area>>()
    val areas: LiveData<List<Area>>
        get() = areasMLD

    private val areaMLD = MutableLiveData<Area>()
    val area: LiveData<Area>
        get() = areaMLD

    fun getAreas(){
        GlobalScope.launch(IO) {
            when (val response = areaRepository.getAreas()) {
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        areasMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }

    fun getArea(id:Int){
        GlobalScope.launch(IO) {
            when (val response = areaRepository.getArea(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        areaMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }

    fun getAreaInformation(id: Int){
        GlobalScope.launch(IO) {
            when (val response = areaRepository.getAreaInformation(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        areaMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }



}