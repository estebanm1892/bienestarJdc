package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable
import retrofit2.Call

class PublicationRepository(private val apiService: MyApi) {
    fun getPublications(): Call<List<Publication>> {
        return apiService.getPublications
    }
}