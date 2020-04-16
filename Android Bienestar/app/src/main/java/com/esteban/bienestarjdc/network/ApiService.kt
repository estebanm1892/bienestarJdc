package com.esteban.bienestarjdc.network

import retrofit2.Response
import java.io.IOException

abstract class ApiService {
    suspend fun <T: Any> ApiRequest(call: suspend() -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return  response.body()!!
        } else {
            throw ApiException(response.code().toString())
        }
    }
}

class ApiException(message: String) : IOException(message)