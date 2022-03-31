package com.example.pepflix.responses

import com.example.pepflix.models.TVShowDetails
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TVShowDetailsResponse(
    @SerializedName("tvShow")
    val tvShowDetails: TVShowDetails

) : Serializable
