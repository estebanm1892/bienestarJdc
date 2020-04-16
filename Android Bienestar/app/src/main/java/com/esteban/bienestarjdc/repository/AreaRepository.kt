package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.network.ApiService
import com.esteban.bienestarjdc.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AreaRepository(private val service: MyApi) {

    suspend fun getAreas(): ApiService<List<Area>> {
        return suspendCoroutine { continuation ->
            service.getAreas.enqueue(object : Callback<List<Area>>{
                override fun onFailure(call: Call<List<Area>>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(call: Call<List<Area>>, response: Response<List<Area>>) {
                    continuation.resume(ApiService.create(response))
                }
            })
        }
    }

    suspend fun getArea(id: Int): ApiService<Area> {
        return suspendCoroutine { continuation ->
            service.getArea(id).enqueue(object: Callback<Area>{
                override fun onFailure(call: Call<Area>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(call: Call<Area>, response: Response<Area>) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

}