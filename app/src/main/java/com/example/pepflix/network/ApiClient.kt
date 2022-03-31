package com.example.pepflix.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit {
            val tempInstance = INSTANCE

            return if (tempInstance == null) {
                val instance = Retrofit.Builder()
                    .baseUrl("https://www.episodate.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                INSTANCE = instance
                instance
            } else {
                tempInstance
            }
        }
    }
}