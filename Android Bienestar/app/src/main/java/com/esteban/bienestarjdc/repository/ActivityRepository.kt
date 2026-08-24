package com.esteban.bienestarjdc.repository


import com.esteban.bienestarjdc.data.AreaActivity
import com.esteban.bienestarjdc.network.ApiService
import com.esteban.bienestarjdc.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ActivityRepository(private val service: MyApi) {

    suspend fun getActivities(id: Int): ApiService<List<AreaActivity>> {
        return suspendCoroutine { continuation ->
            service.getActivities(id).enqueue(object : Callback<List<AreaActivity>>{
                override fun onFailure(call: Call<List<AreaActivity>>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(
                    call: Call<List<AreaActivity>>,
                    response: Response<List<AreaActivity>>
                ) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

}