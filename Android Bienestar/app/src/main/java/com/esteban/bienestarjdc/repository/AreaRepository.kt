package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable


class AreaRepository(private val apiService: MyApi) {
    fun getAreas(): Observable<List<Area>> {
        return apiService.getAreas
    }
}