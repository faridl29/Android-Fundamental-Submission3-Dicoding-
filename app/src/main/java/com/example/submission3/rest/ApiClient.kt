package com.example.submission3.rest

import com.example.submission3.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{

    private var BASE_URL = "https://api.github.com/"

    fun providesHttpAdapter(): Retrofit {
        return Retrofit.Builder().apply {
            client(providesHttpClient())
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())}.build()
    }

    private fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addInterceptor(providesHttpLoggingIntercepter())}.build()
    }

    private fun providesHttpLoggingIntercepter(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG){
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }}
    }
}