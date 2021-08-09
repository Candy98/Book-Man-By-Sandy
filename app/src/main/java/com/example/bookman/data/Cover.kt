package com.example.bookman.data

import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String
)