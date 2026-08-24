package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.VirtualResource
import com.esteban.bienestarjdc.network.ApiService
import com.esteban.bienestarjdc.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class VirtualResourceRepository(private val service: MyApi) {

    suspend fun getVirtualResources(id: Int): ApiService<List<VirtualResource>> {
        return suspendCoroutine { continuation ->
            service.getVirtualResources(id).enqueue(object : Callback<List<VirtualResource>> {
                override fun onFailure(call: Call<List<VirtualResource>>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(
                    call: Call<List<VirtualResource>>,
                    response: Response<List<VirtualResource>>
                ) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

    suspend fun getVirtualResource(id: Int): ApiService<VirtualResource> {
        return suspendCoroutine { continuation ->
            service.getVirtualResource(id).enqueue(object: Callback<VirtualResource>{
                override fun onFailure(call: Call<VirtualResource>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(
                    call: Call<VirtualResource>,
                    response: Response<VirtualResource>
                ) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

}