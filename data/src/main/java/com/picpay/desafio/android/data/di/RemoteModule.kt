package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.source.remote.service.PicPayService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val CONNECT_TIMEOUT = 10_000L
    private const val WRITE_TIMEOUT = 10_000L
    private const val READ_TIMEOUT = 30_000L
    private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideBaseRetrofit(
        moshi: Moshi
    ): Retrofit {
        val okHttpClient = provideOkHttpClient()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun provideOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor.Logger { message ->
            Platform.get().log(message, Platform.INFO, null)
        }
        val loggingInterceptor = HttpLoggingInterceptor(logger)
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            interceptors.forEach(::addInterceptor)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun providePicPayService(retrofit: Retrofit): PicPayService =
        retrofit.create(PicPayService::class.java)

}
