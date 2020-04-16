package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable

class PublicationRepository(private val apiService: MyApi) {
    fun getPublications(): Observable<List<Publication>> {
        return apiService.getPublications
    }
}