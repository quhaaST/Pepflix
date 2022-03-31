package com.example.pepflix.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TVShowDetails(
    @SerializedName("url")
    val url: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("image_path")
    val imagePath: String,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("genres")
    val genres: List<String>,

    @SerializedName("pictures")
    val pictures: List<String>,

    @SerializedName("episodes")
    val episodes: List<Episode>

) : Serializable
