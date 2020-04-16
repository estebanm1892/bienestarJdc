package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable
import retrofit2.Call

class NormativeRepository(private val apiService: MyApi) {
    fun getNormatives(): Call<List<Normative>> {
        return apiService.getNormatives
    }
}