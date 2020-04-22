package com.esteban.bienestarjdc.repository

import com.esteban.bienestarjdc.data.Prepregistrer
import com.esteban.bienestarjdc.network.ApiService
import com.esteban.bienestarjdc.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PreregisterRepository(private val service: MyApi) {

    suspend fun addPreregister(id: Int): ApiService<Prepregistrer>{
        return suspendCoroutine { continuation ->
            service.addPreregister(newPreregister = Prepregistrer(), id = id).enqueue(object : Callback<Prepregistrer> {
                override fun onFailure(call: Call<Prepregistrer>, t: Throwable) {
                    continuation.resume(ApiService.create(t))
                }

                override fun onResponse(
                    call: Call<Prepregistrer>,
                    response: Response<Prepregistrer>
                ) {
                    continuation.resume(ApiService.create(response))
                }

            })
        }
    }

}