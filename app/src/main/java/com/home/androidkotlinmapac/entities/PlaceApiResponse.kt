package com.home.androidkotlinmapac.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlaceApiResponse(

    @SerializedName("html_attributions")
    @Expose
    var htmlAttributions: List<Any>?,
    @SerializedName("next_page_token")
    @Expose
    var nextPageToken: String?,
    @SerializedName("results")
    @Expose
    var results: List<Result>?,
    @SerializedName("status")
    @Expose
    var status: String?
)
