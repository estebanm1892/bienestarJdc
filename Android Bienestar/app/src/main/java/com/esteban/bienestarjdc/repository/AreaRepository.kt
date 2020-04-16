package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable
import retrofit2.Call


class AreaRepository(private val apiService: MyApi) {
    fun getAreas(): Call<List<Area>> {
        return apiService.getAreas
    }
}