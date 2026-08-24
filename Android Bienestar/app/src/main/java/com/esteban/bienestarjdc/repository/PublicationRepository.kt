package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.ApiService
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PublicationRepository(private val service: MyApi) {

    suspend fun getPublications(): ApiService<List<Publication>> {
        return suspendCoroutine { continuation ->
            service.getPublications.enqueue(object : Callback<List<Publication>>{
                override fun onFailure(call: Call<List<Publication>>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(
                    call: Call<List<Publication>>,
                    response: Response<List<Publication>>
                ) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

    suspend fun getPublication(id: Int): ApiService<Publication>{
        return suspendCoroutine { continuation ->
            service.getPublication(id).enqueue(object : Callback<Publication>{
                override fun onFailure(call: Call<Publication>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(call: Call<Publication>, response: Response<Publication>) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

}