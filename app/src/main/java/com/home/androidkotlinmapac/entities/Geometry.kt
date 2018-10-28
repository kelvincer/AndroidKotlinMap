package com.home.androidkotlinmapac.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("location")
    @Expose
    var location: Location?,
    @SerializedName("viewport")
    @Expose
    var viewport: Viewport?
)