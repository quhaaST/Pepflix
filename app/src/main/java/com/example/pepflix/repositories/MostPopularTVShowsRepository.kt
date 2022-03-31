package com.example.pepflix.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pepflix.network.ApiClient
import com.example.pepflix.network.ApiService
import com.example.pepflix.responses.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularTVShowsRepository {
    private var apiService: ApiService = ApiClient.getInstance().create(ApiService::class.java)

    fun getMostPopularTVShows(page: Int): LiveData<TVShowResponse> {
        val data: MutableLiveData<TVShowResponse> = MutableLiveData<TVShowResponse>()
        apiService.getMostPopularTVShows(page).enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
            }
        })
        return data
    }
}