package com.example.pepflix.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pepflix.models.TVShow

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tv_shows")
    fun getWatchList() : LiveData<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(tvShow: TVShow)

    @Delete
    suspend fun removeFromWatchlist(tvShow: TVShow)

    @Query("SELECT * FROM tv_shows WHERE id = :tvShowId")
    fun getTVShowFromWatchlist(tvShowId: Int): TVShow?
}