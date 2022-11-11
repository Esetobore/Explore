package com.example.explore.util

import com.example.explore.api.ExploreApi
import com.example.explore.util.Constant.Companion.BASE_URL
import com.example.explore.util.Retrofit.Companion.api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    companion object{
        private val retrofitExplore by lazy {
            val client = OkHttpClient.Builder()
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy{
            retrofitExplore.create(ExploreApi::class.java)
        }


    }
}