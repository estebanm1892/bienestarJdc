package com.esteban.bienestarjdc.network

import com.esteban.bienestarjdc.data.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    fun getArea(@Path("id") id: Int): Call<Area>

    @GET("area_information/{id}")
    fun getAreaInformation(@Path("id") id: Int): Call<Area>

    /*
    Activities Routes
     */

    @GET("area_activities/{id}")
    fun getActivities(@Path("id")id: Int): Call<List<AreaActivity>>

    @POST("activity/{id}/preregister")
    fun addPreregister(@Body newPreregister: Prepregistrer, @Path("id")id: Int): Call<Prepregistrer>

    @GET("activity_vresources/{id}")
    fun getVirtualResources(@Path("id")id: Int): Call<List<VirtualResource>>

    @GET("vresource/{id}")
    fun getVirtualResource(@Path("id")id: Int): Call<VirtualResource>

    /*
    Publications Routes
     */
    @get:GET("publications")
    val  getPublications: Call<List<Publication>>

    @GET("publication/{id}")
    fun getPublication(@Path("id") id: Int): Call<Publication>


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