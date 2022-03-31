package com.example.pepflix.responses

import com.example.pepflix.models.TVShow
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TVShowResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("pages")
    val totalPages: Int,

    @SerializedName("tv_shows")
    val tvShows: List<TVShow>
) : Serializable
