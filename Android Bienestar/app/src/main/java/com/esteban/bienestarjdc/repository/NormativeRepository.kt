package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable

class NormativeRepository(private val apiService: MyApi) {
    fun getNormatives(): Observable<List<Normative>> {
        return apiService.getNormatives
    }
}