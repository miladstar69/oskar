package com.example.oskar.data.remote

import com.example.oskar.data.remote.dto.MainModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.net.SocketException
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object {

        private var apiRequest2: ApiService? = null
        private var apiRequest3: ApiService? = null
        fun getInstance2(): ApiService {
            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://affectionate-tereshkova-q5c98zf1bj.projects.oryapis.com")
                .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
            apiRequest2 = retrofit.create(ApiService::class.java)
            return apiRequest2!!
        }

        fun getInstance3(): ApiService {

            var okHttpClient: OkHttpClient? = null
            try {
                okHttpClient = OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val newRequest: Request =
                            chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json").build()
                        chain.proceed(newRequest)
                    }.build()
            } catch (e: SocketException) {
                e.printStackTrace()
            }

            val gson = GsonBuilder().setLenient().create()


            val retrofit = Retrofit.Builder()
                .baseUrl("https://affectionate-tereshkova-q5c98zf1bj.projects.oryapis.com")
                .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create()).build()
            apiRequest3 = retrofit.create(ApiService::class.java)
            return apiRequest3!!
        }


    }

    @GET("/self-service/registration/api")
    suspend fun getRegister(): Response<MainModel>

    @GET("/self-service/login/api")
    suspend fun getLogin(): Response<MainModel>


    @POST("/self-service/registration")
    suspend fun sendRegister(
        @Query("flow") flowId: String,
        @Body map: Map<String, String>,
    ): Response<ResponseBody>

    @POST("/self-service/login")
    suspend fun sendLogin(
        @Query("flow") flowId: String,
        @Body map: Map<String, String>,
    ): Response<ResponseBody>


}