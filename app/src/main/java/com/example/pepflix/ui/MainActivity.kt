package com.example.pepflix.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pepflix.adapters.TVShowAdapter
import com.example.pepflix.databinding.ActivityMainBinding
import com.example.pepflix.listeners.TVShowListener
import com.example.pepflix.models.TVShow
import com.example.pepflix.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity(), TVShowListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MostPopularTVShowsViewModel
    private val tvShows = ArrayList<TVShow>()
    private lateinit var tvShowAdapter: TVShowAdapter
    private var currentPage = 1
    private var totalAvailablePages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()
    }

    private fun doInitialization() {
        binding.rvTvShows.setHasFixedSize(true)
        viewModel = ViewModelProvider(this)[MostPopularTVShowsViewModel::class.java]
        tvShowAdapter = TVShowAdapter(tvShows, this)

        binding.rvTvShows.adapter = tvShowAdapter
        binding.rvTvShows.layoutManager = LinearLayoutManager(this)
        binding.rvTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvTvShows.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        getMostPopularTVShows()
                    }
                }
            }
        })
        getMostPopularTVShows()

        binding.imageWatchList.setOnClickListener {
            val intent = Intent(applicationContext, WatchlistActivity::class.java)
            startActivity(intent)
        }

        binding.imageSearch.setOnClickListener {
            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getMostPopularTVShows() {
        toggleLoading()
        viewModel.getMostPopularTVShows(currentPage).observe(this, Observer { response ->
            toggleLoading()
            if (response != null) {
                totalAvailablePages = response.totalPages
                val oldCount = tvShows.size
                tvShows.addAll(response.tvShows)
                tvShowAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
            }
        })
    }

    private fun toggleLoading() {
        if (currentPage == 1) {
            if (binding.pbTvShows.visibility == View.GONE) {
                binding.pbTvShows.visibility = View.VISIBLE
            } else {
                binding.pbTvShows.visibility = View.GONE
            }
        } else {
            if (binding.pbTvShowsMore.visibility == View.GONE) {
                binding.pbTvShowsMore.visibility = View.VISIBLE
            } else {
                binding.pbTvShowsMore.visibility = View.GONE
            }
        }
    }

    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)

        startActivity(intent)
    }

}