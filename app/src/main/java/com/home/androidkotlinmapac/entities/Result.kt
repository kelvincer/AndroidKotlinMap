package com.home.androidkotlinmapac.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(

    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String?,
    @SerializedName("geometry")
    @Expose
    var geometry: Geometry?,
    @SerializedName("icon")
    @Expose
    var icon: String?,
    @SerializedName("id")
    @Expose
    var id: String?,
    @SerializedName("name")
    @Expose
    var name: String?,
    @SerializedName("opening_hours")
    @Expose
    var openingHours: OpeningHours?,
    @SerializedName("photos")
    @Expose
    var photos: List<Photo>?,
    @SerializedName("place_id")
    @Expose
    var placeId: String?,
    @SerializedName("rating")
    @Expose
    var rating: Double?,
    @SerializedName("reference")
    @Expose
    var reference: String?,
    @SerializedName("types")
    @Expose
    var types: List<String>?,
    @SerializedName("vicinity")
    @Expose
    var vicinity: String?
)
