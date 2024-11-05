package com.example.mobileapplication.network

import com.example.mobileapplication.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


object RetrofitClient {
    private const val BASE_URL = "https://projet-annuel-1.onrender.com"

    val client = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient().newBuilder().addInterceptor{ chain ->
                        chain.proceed(
                            chain.request()
                                .newBuilder()
                                .build()
                        )
                    }.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)


}