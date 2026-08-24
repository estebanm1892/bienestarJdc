package com.esteban.bienestarjdc.ui.publication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.ApiException
import com.esteban.bienestarjdc.network.ApiSuccess
import com.esteban.bienestarjdc.repository.PublicationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class PublicationViewModel(private val publicationRepository: PublicationRepository) : ViewModel() {

    private val publicationsMLD = MutableLiveData<List<Publication>>()
    val publications: LiveData<List<Publication>>
        get() = publicationsMLD

    private val publicationMLD = MutableLiveData<Publication>()
    val publication: LiveData<Publication>
        get() = publicationMLD

    fun getPublications(){
        GlobalScope.launch(IO) {
            when(val response = publicationRepository.getPublications()){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        publicationsMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }

    fun getPublication(id:Int){
        GlobalScope.launch(IO) {
            when ( val response = publicationRepository.getPublication(id)){
                is ApiSuccess -> {
                    GlobalScope.launch(Main) {
                        publicationMLD.value = response.value
                    }
                }
                is ApiException -> Log.d("ERROR", response.message)
            }
        }
    }
}