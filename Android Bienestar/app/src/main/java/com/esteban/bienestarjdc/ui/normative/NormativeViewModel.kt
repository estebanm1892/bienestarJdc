package com.esteban.bienestarjdc.ui.normative

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.network.ApiException
import com.esteban.bienestarjdc.network.ApiSuccess
import com.esteban.bienestarjdc.repository.NormativeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class NormativeViewModel(private val normativeRepository: NormativeRepository): ViewModel() {

    private val normativesMLD = MutableLiveData<List<Normative>>()
    val normatives: LiveData<List<Normative>>
        get() = normativesMLD

    fun getNormatives(){
        GlobalScope.launch(IO) {
            when (val response = normativeRepository.getNormatives()) {
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        normativesMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }

}