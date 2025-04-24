package com.example.realnerva
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/register") //H opou to einai to endpoint pou tha steilei to request
    fun registerUser(@Body user: User): Call<Void>
}