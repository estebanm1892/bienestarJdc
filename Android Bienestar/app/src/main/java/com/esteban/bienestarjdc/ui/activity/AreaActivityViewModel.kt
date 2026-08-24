package com.esteban.bienestarjdc.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.AreaActivity
import com.esteban.bienestarjdc.network.ApiException
import com.esteban.bienestarjdc.network.ApiSuccess
import com.esteban.bienestarjdc.repository.ActivityRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AreaActivityViewModel (private val areaActivityRepository: ActivityRepository) : ViewModel() {

    private val areaActivitiesMLD = MutableLiveData<List<AreaActivity>>()
    val areactivities: LiveData<List<AreaActivity>>
        get() = areaActivitiesMLD

    fun getActivities(id: Int){
        GlobalScope.launch(IO) {
            when (val response = areaActivityRepository.getActivities(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        areaActivitiesMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }

}