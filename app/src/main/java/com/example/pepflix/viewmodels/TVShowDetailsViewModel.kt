package com.example.pepflix.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pepflix.repositories.TVShowDetailsRepository
import com.example.pepflix.responses.TVShowDetailsResponse

class TVShowDetailsViewModel : ViewModel() {
    private val tvShowDetailsRepository = TVShowDetailsRepository()

    fun getTVShowDetails(tvShowId: Int): LiveData<TVShowDetailsResponse> {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }
}