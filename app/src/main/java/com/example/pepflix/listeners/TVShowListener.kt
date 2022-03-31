package com.example.pepflix.listeners

import com.example.pepflix.models.TVShow

interface TVShowListener {
    fun onTVShowClicked(tvShow: TVShow)
}