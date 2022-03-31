package com.example.pepflix.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Episode(
    @SerializedName("season")
    val season: Int,

    @SerializedName("episode")
    val episode: Int,

    @SerializedName("name")
    val title: String,

    @SerializedName("air_date")
    val airDate: String
) : Serializable
