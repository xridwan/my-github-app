package com.xridwan.mygithub.di

import com.xridwan.mygithub.BuildConfig
import com.xridwan.mygithub.data.UserRepositoryImpl
import com.xridwan.mygithub.data.local.LocalDataSource
import com.xridwan.mygithub.data.remote.RemoteDataSource
import com.xridwan.mygithub.data.remote.network.ApiService
import com.xridwan.mygithub.domain.repository.UserRepository
import com.xridwan.mygithub.utils.AppExecutors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val interceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
                addInterceptor(Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                        .addHeader("Authorization", BuildConfig.MY_TOKEN)
                        .header("Content-Type", "application/json")
                    return@Interceptor chain.proceed(builder.build())
                })
            }.build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

val dataSourceModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    factory { AppExecutors() }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get(), get())
    }
}