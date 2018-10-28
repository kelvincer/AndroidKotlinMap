package com.home.androidkotlinmapac

import com.home.androidkotlinmapac.entities.PlaceApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("textsearch/json")
    fun getTextResults(
        @Query("query") query: String, // required
        @Query("key") key: String
    ): Call<PlaceApiResponse>
}