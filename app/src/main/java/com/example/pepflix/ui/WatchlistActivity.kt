package com.example.pepflix.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pepflix.adapters.WatchlistAdapter
import com.example.pepflix.databinding.ActivityWatchlistBinding
import com.example.pepflix.listeners.WatchlistListener
import com.example.pepflix.models.TVShow
import com.example.pepflix.utilities.TempDataHolder
import com.example.pepflix.viewmodels.WatchlistViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn

class WatchlistActivity : AppCompatActivity(), WatchlistListener {
    private lateinit var binding: ActivityWatchlistBinding
    private lateinit var watchlistViewModel: WatchlistViewModel
    private lateinit var watchlistAdapter: WatchlistAdapter
    private var watchlist = mutableListOf<TVShow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()
    }

    private fun doInitialization() {
        watchlistViewModel = ViewModelProvider(this)[WatchlistViewModel::class.java]
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        loadWatchlist()
    }

    private fun loadWatchlist() {
        binding.pbLoading.visibility = View.VISIBLE
        watchlistViewModel.watchlist.observe(this, Observer { tvShows ->
            binding.pbLoading.visibility = View.GONE
            if (watchlist.isNotEmpty()) {
                watchlist.clear()
            }
            watchlist.addAll(tvShows)
            watchlistAdapter = WatchlistAdapter(watchlist, this)
            binding.rvWatchlistEpisodes.adapter = watchlistAdapter
            binding.rvWatchlistEpisodes.visibility = View.VISIBLE
        })
    }


    override fun onResume() {
        super.onResume()
        if (TempDataHolder.IS_WATCHLIST_UPDATED) {
            TempDataHolder.IS_WATCHLIST_UPDATED = false
            loadWatchlist()
        }
    }

    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }

    override fun removeTVShowFromWatchlist(tvShow: TVShow, position: Int) {
        watchlistViewModel.removeTVShowFromWatchlist(tvShow)
        watchlist.removeAt(position)
        watchlistAdapter.notifyItemRemoved(position)
        watchlistAdapter.notifyItemRangeChanged(position, watchlistAdapter.itemCount)
    }
}