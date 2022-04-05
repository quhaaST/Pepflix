package com.example.pepflix.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.pepflix.adapters.TVShowAdapter
import com.example.pepflix.databinding.ActivitySearchBinding
import com.example.pepflix.listeners.TVShowListener
import com.example.pepflix.models.TVShow
import com.example.pepflix.viewmodels.SearchViewModel
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

class SearchActivity : AppCompatActivity(), TVShowListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var tvShowsAdapter: TVShowAdapter
    private var timer: Timer? = null
    private var tvShows = mutableListOf<TVShow>()
    private var currentPage = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()
    }

    private fun doInitialization() {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.rvTvShows.setHasFixedSize(true)
        tvShowsAdapter = TVShowAdapter(tvShows, this)
        binding.rvTvShows.adapter = tvShowsAdapter

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().trim().isNotEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                currentPage = 1
                                totalPages = 1
                                searchTVShow(p0.toString())
                            }
                        }

                    }, 800)
                } else {
                    tvShows.clear()
                    tvShowsAdapter.notifyDataSetChanged()
                }
            }
        })

        binding.rvTvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvTvShows.canScrollVertically(1)) {
                    if (binding.etSearch.text.toString().isNotEmpty()) {
                        if (currentPage < totalPages) {
                            currentPage += 1
                            searchTVShow(binding.etSearch.text.toString())
                        }
                    }
                }
            }
        })
        binding.etSearch.requestFocus()
    }

    private fun searchTVShow(query: String) {
        toggleLoading()
        searchViewModel.searchTVShow(query, currentPage).observe(this, Observer { response ->
            toggleLoading()
            if (response != null) {
                totalPages = response.totalPages

                val oldCount = tvShows.size
                tvShows.addAll(response.tvShows)
                tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
            }
        })
    }

    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
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
}