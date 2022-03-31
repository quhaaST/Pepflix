package com.example.pepflix.network

import com.example.pepflix.responses.TVShowDetailsResponse
import com.example.pepflix.responses.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("most-popular")
    fun getMostPopularTVShows(
        @Query("page") page: Int
    ): Call<TVShowResponse>

    @GET("show-details")
    fun getTVShowDetails(
        @Query("q") id: Int
    ) : Call<TVShowDetailsResponse>
}