package com.elementarylogics.imagesliderapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

//        val BASE_URL = "https://elxdrive.com/dalieurope/"
val BASE_URL = "https://hummart.herokuapp.com/"
        //https://hummart.herokuapp.com
//        https://documenter.getpostman.com/view/101854/SzKZtGYQ?version=latest#9df60811-a0bd-5ab8-e522-ff628c702114

        private var retrofit: Retrofit? = null


      public  fun getClient(): Retrofit? {
            val gson = GsonBuilder().setLenient().create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
    }
}