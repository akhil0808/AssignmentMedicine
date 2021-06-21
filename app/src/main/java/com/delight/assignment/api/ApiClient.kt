package com.delight.assignment.api

import com.delight.assignment.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    fun getApiService(): ApiService {
        val apiPath =  BuildConfig.BASE_URL

        return Retrofit.Builder()
            .baseUrl(apiPath)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .build().create(ApiService::class.java)
    }
}


/**
 * @method use for provide ok Http client
 */
private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.addInterceptor(interceptor)
    okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
    okHttpClient.readTimeout(60, TimeUnit.SECONDS)
    return okHttpClient.build()
}

/**
 * @method use for provide Http Logging Interceptor
 */
private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

