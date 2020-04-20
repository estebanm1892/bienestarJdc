package com.esteban.bienestarjdc.ui.vresources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.VirtualResource
import com.esteban.bienestarjdc.network.ApiException
import com.esteban.bienestarjdc.network.ApiSuccess
import com.esteban.bienestarjdc.repository.VirtualResourceRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VirtualResourcesViewModel (private val virtualResourceRepository: VirtualResourceRepository) : ViewModel() {

    private val virtualResourcesMLD = MutableLiveData<List<VirtualResource>>()
    val virtualResources: LiveData<List<VirtualResource>>
        get() = virtualResourcesMLD

    private val virtualResourceMLD = MutableLiveData<VirtualResource>()
    val virtualResource: LiveData<VirtualResource>
        get() = virtualResourceMLD

    fun getVirtualResources(id: Int){
        GlobalScope.launch(IO) {
            when(val response = virtualResourceRepository.getVirtualResources(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        virtualResourcesMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }

    fun getVirtualResource(id: Int){
        GlobalScope.launch(IO) {
            when (val response = virtualResourceRepository.getVirtualResource(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        virtualResourceMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("Error", response.message)
            }
        }
    }

}