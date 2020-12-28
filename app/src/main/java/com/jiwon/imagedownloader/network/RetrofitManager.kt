package com.jiwon.imagedownloader.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object {
        const val BASE_API_ADDRESS = "https://openapi.naver.com"
        const val CLIENT_ID = "개인 CLIENT_ID"
        const val CLIENT_SECRET = "개인 CLIENT_SECRET"

        private lateinit var mRetrofit: Retrofit

        fun getRetrofit(): Retrofit {
            return if (::mRetrofit.isInitialized) {
                mRetrofit
            } else {
                initRetrofit()
            }
        }

        private fun initRetrofit(): Retrofit {
            // Log
            val httpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            // Add Header
            val requestInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()
                chain.proceed(request)
            }

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }
}