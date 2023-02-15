package com.xridwan.mygithub.data.network

import com.xridwan.mygithub.helper.Helper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .apply {
                addInterceptor(Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                        .addHeader("Authorization", Helper.TOKEN)
                        .header("Content-Type", "application/json")
                    return@Interceptor chain.proceed(builder.build())
                })
            }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Helper.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}