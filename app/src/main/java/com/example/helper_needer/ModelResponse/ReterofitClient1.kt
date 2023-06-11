package com.example.helper_needer.ModelResponse

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReterofitClient1 {

    val api : Api by lazy{
        Retrofit.Builder()
            .baseUrl("http://192.168.0.138\\Help_Need\\")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}