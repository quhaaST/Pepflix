package com.example.pepflix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pepflix.databinding.ActivityTvshowDetailsBinding
import com.example.pepflix.models.TVShow
import com.example.pepflix.viewmodels.TVShowDetailsViewModel

class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvshowDetailsBinding
    private lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvshowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()

    }

    private fun doInitialization() {
        tvShowDetailsViewModel = ViewModelProvider(this)[TVShowDetailsViewModel::class.java]
        getTVShowDetails()
    }

    private fun getTVShowDetails() {
        binding.pbTvShowDetails.visibility = View.VISIBLE
        val currentTVShow: TVShow = intent.extras?.get("tvShow") as TVShow
        tvShowDetailsViewModel.getTVShowDetails(currentTVShow.id).observe(this, Observer {
            binding.pbTvShowDetails.visibility = View.GONE
            Toast.makeText(this, it.tvShowDetails.description, Toast.LENGTH_SHORT).show()
        })
    }
}