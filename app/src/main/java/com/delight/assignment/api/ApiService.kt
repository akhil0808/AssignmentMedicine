package com.delight.assignment.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/medicines")
    fun getAllMedicines(): Call<ResponseBody>

   }