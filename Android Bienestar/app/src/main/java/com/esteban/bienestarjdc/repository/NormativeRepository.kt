package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.network.ApiService
import com.esteban.bienestarjdc.network.MyApi
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NormativeRepository(private val service: MyApi) {

    suspend fun getNormatives(): ApiService<List<Normative>> {
        return suspendCoroutine { continuation ->
            service.getNormatives.enqueue(object : Callback<List<Normative>>{
                override fun onFailure(call: Call<List<Normative>>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(
                    call: Call<List<Normative>>,
                    response: Response<List<Normative>>
                ) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }
}