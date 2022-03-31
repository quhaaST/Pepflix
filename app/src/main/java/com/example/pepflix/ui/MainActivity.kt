package com.example.pepflix.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pepflix.adapters.TVShowAdapter
import com.example.pepflix.databinding.ActivityMainBinding
import com.example.pepflix.models.TVShow
import com.example.pepflix.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MostPopularTVShowsViewModel
    private val tvShows = ArrayList<TVShow>()
    private lateinit var tvShowAdapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()
    }

    private fun doInitialization() {
        binding.rvTvShows.setHasFixedSize(true)
        viewModel = ViewModelProvider(this)[MostPopularTVShowsViewModel::class.java]
        tvShowAdapter = TVShowAdapter(tvShows)

        binding.rvTvShows.adapter = tvShowAdapter
        binding.rvTvShows.layoutManager = LinearLayoutManager(this)
        getMostPopularTVShows()
    }

    private fun getMostPopularTVShows() {
        viewModel.getMostPopularTVShows(0).observe(this, Observer {
            if (it != null) {
                tvShows.addAll(it.tvShows)
                tvShowAdapter.notifyDataSetChanged()
            }
        })
    }

}