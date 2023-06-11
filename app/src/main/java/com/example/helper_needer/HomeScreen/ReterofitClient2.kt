package com.example.helper_needer.HomeScreen

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ReterofitClient2 {

    val api : Api2 by lazy{
        Retrofit.Builder()
            .baseUrl("http://192.168.0.138\\Help_Need\\")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api2::class.java)
    }
}