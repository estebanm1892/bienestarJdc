package com.esteban.bienestarjdc.network

import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.data.Publication
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://10.0.2.2:8000/api/"
const val IMAGE_URL = "http://10.0.2.2:8000"

interface MyApi {

    /*
    Areas Routes
     */

    @get:GET("areas")
    val getAreas: Call<List<Area>>

    @GET("area/{id}")
    fun getArea(@Path("id") id: Int): Call<List<Area>>

    /*
    Publications Routes
     */
    @get:GET("publications")
    val  getPublications: Call<List<Publication>>

    @GET("publication/{id}")
    fun getPublication(@Path("id") id: Int): Call<List<Publication>>


    /*
    Normatives Routes
     */
    @get:GET("normatives")
    val  getNormatives: Call<List<Normative>>

    companion object RetrofitObject {
        operator fun invoke(): MyApi {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.newBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MyApi::class.java)
        }


    }





}