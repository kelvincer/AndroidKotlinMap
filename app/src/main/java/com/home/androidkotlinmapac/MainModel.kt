package com.home.androidkotlinmapac

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.home.androidkotlinmapac.entities.PlaceApiResponse
import com.home.androidkotlinmapac.entities.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainModel {

    internal val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
    internal val GOOGLE_PLACE_API_KEY = "AIzaSyCwmYvGIV7owfcc7muneajVaIz6cXKA8Wg"

    private val result = MutableLiveData<List<Result>>()

    fun getResult(): LiveData<List<Result>> {
        return result
    }

    fun getData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        val call = service.getTextResults("restaurantes en lima", GOOGLE_PLACE_API_KEY)
        call.enqueue(object : Callback<PlaceApiResponse> {
            override fun onResponse(call: Call<PlaceApiResponse>, response: Response<PlaceApiResponse>) {
                if (response.isSuccessful()) {
                    val res = response.body()
                    result.value = res?.results
                }
            }

            override fun onFailure(call: Call<PlaceApiResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
