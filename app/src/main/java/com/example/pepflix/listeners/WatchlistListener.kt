package com.example.pepflix.listeners

import com.example.pepflix.models.TVShow

interface WatchlistListener {
    fun onTVShowClicked(tvShow: TVShow)

    fun removeTVShowFromWatchlist(tvShow: TVShow, position: Int)
}