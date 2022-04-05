package com.example.pepflix.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pepflix.network.ApiClient
import com.example.pepflix.network.ApiService
import com.example.pepflix.responses.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class SearchTVShowsRepository {
    private var apiService = ApiClient.getInstance().create(ApiService::class.java)

    fun searchTVShows(query: String, page: Int): LiveData<TVShowResponse> {
        val data = MutableLiveData<TVShowResponse>()
        apiService.searchTVShows(query, page).enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return data
    }
}