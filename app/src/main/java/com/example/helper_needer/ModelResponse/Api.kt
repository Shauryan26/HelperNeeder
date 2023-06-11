package com.example.helper_needer.ModelResponse

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("register.php")
    fun setProfile(
        @Field("name") name : String,
        @Field("age") age : Int,
        @Field("phone") phone : String,
        @Field("gender") gender : String,
        @Field("duration") duration : String,
        @Field("city") city : String,
        @Field("Rooms") rooms : Int,
        @Field("tasks") tasks : Int,
        @Field("type") type : String

                   ): Call<RegisterResponse>
}