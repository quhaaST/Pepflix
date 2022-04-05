package com.example.pepflix.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pepflix.database.TVShowsDatabase
import com.example.pepflix.models.TVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class WatchlistViewModel(application: Application): AndroidViewModel(application) {
    private val tvShowsDatabase = TVShowsDatabase.getDatabase(application)
    var watchlist: LiveData<List<TVShow>> = tvShowsDatabase.tvShowsDao().getWatchList()

    fun removeTVShowFromWatchlist(tvShow: TVShow) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowsDatabase.tvShowsDao().removeFromWatchlist(tvShow)
        }
    }
}