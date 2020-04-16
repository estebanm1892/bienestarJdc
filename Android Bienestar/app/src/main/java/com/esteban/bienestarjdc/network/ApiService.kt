package com.esteban.bienestarjdc.network

import retrofit2.Response

sealed class ApiService<T> {
    companion object {

        fun <T> create(t: Throwable): ApiService<T> {
            return ApiException(message = "${t.message}")
        }

        fun <T> create (response: Response<T>): ApiService<T> {
            if (response.isSuccessful) {
                response.body()?.let {
                    return ApiSuccess(value = it)
                }
                return ApiException(message = "No body")
            } else {
                return ApiException(message = "Error")
            }
        }
    }
}

class ApiSuccess<T>(val value: T): ApiService<T>()

class ApiException<T>(val message: String) : ApiService<T>()