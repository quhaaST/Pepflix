package com.example.pepflix.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pepflix.network.ApiClient
import com.example.pepflix.network.ApiService
import com.example.pepflix.responses.TVShowDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {
    private var apiService = ApiClient.getInstance().create(ApiService::class.java)

    fun getTVShowDetails(tvShowId: Int) : LiveData<TVShowDetailsResponse> {
        val data = MutableLiveData<TVShowDetailsResponse>()
        apiService.getTVShowDetails(tvShowId).enqueue(object : Callback<TVShowDetailsResponse> {
            override fun onResponse(
                call: Call<TVShowDetailsResponse>,
                response: Response<TVShowDetailsResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {}
        })
        return data
    }
}