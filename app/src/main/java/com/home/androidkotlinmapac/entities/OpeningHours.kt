package com.home.androidkotlinmapac.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OpeningHours(

    @SerializedName("open_now")
    @Expose
    var openNow: Boolean?,
    @SerializedName("weekday_text")
    @Expose
    var weekdayText: List<Any>? = null

)
