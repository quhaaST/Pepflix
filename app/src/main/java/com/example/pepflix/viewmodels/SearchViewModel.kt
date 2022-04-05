package com.example.pepflix.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pepflix.models.TVShow
import com.example.pepflix.repositories.SearchTVShowsRepository
import com.example.pepflix.responses.TVShowResponse

class SearchViewModel : ViewModel() {
    private val searchTVShowsRepository = SearchTVShowsRepository()

    fun searchTVShow(query: String, page: Int): LiveData <TVShowResponse> {
        return searchTVShowsRepository.searchTVShows(query, page)
    }
}