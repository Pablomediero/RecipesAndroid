package com.hiberus.receptom.data.remote.service

import com.hiberus.receptom.data.remote.remote_const.Const
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private fun makeHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(makeLogginInterceptor(true))
            .addInterceptor { chain ->
                val newRequest: Request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer ${Const.API_KEY}")
                        .build()
                chain.proceed(newRequest)
            }.build()

    fun provideApiService(): ChatGPTService =
        Retrofit.Builder()
            .baseUrl(Const.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(makeHttpClient())
            .build()
            .create(ChatGPTService::class.java)

    private fun makeLogginInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val loggin = HttpLoggingInterceptor()
        loggin.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return loggin
    }

}