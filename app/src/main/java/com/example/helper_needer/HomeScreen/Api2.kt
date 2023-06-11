package com.example.helper_needer.HomeScreen

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api2 {
    @FormUrlEncoded
    @POST("fetch.php")
    fun setType(
        @Field("city") city : String,
        @Field("type") type : String

                   ): Call<Data1>
}