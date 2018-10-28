package com.home.androidkotlinmapac.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Northeast(

    @SerializedName("lat")
    @Expose
    var lat: Double?,
    @SerializedName("lng")
    @Expose
    var lng: Double?
)