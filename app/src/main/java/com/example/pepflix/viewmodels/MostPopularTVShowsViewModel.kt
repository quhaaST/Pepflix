package com.example.pepflix.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pepflix.repositories.MostPopularTVShowsRepository
import com.example.pepflix.responses.TVShowResponse

class MostPopularTVShowsViewModel : ViewModel() {
    private val mostPopularTVShowsRepository = MostPopularTVShowsRepository()

    fun getMostPopularTVShows(page: Int): LiveData<TVShowResponse> {
        return mostPopularTVShowsRepository.getMostPopularTVShows(page)
    }
}