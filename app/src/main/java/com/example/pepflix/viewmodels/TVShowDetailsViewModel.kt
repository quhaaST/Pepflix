package com.example.pepflix.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pepflix.database.TVShowsDatabase
import com.example.pepflix.models.TVShow
import com.example.pepflix.repositories.TVShowDetailsRepository
import com.example.pepflix.responses.TVShowDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TVShowDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val tvShowDetailsRepository = TVShowDetailsRepository()
    private val tvShowDatabase = TVShowsDatabase.getDatabase(application)

    fun getTVShowDetails(tvShowId: Int): LiveData<TVShowDetailsResponse> {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }

    fun addToWatchlist(tvShow: TVShow) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowDatabase.tvShowsDao().addToWatchList(tvShow)
        }
    }

    fun removeTVShowFromWatchlist(tvShow: TVShow) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowDatabase.tvShowsDao().removeFromWatchlist(tvShow)
        }
    }

    fun getTVShowFromWatchlist(tvShowId: Int): TVShow? {
        return runBlocking {
            withContext(Dispatchers.IO) {
                tvShowDatabase.tvShowsDao().getTVShowFromWatchlist(tvShowId)
            }
        }
    }
}