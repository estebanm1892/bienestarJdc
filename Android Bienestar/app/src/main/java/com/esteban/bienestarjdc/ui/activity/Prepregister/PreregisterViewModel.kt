package com.esteban.bienestarjdc.ui.activity.Prepregister

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Prepregistrer
import com.esteban.bienestarjdc.network.ApiException
import com.esteban.bienestarjdc.network.ApiSuccess
import com.esteban.bienestarjdc.repository.PreregisterRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class PreregisterViewModel (private val preregisterRepository: PreregisterRepository): ViewModel() {

    private val preregisterMLD = MutableLiveData<Prepregistrer>()
    val preregister: LiveData<Prepregistrer>
        get() = preregisterMLD

    fun addPreregister(id: Int){
        GlobalScope.launch(IO) {
            when (val response = preregisterRepository.addPreregister(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        preregisterMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("Error", response.message)
            }
        }
    }

}