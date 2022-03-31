package com.example.pepflix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pepflix.R
import com.example.pepflix.adapters.ImageSliderAdapter
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
        tvShowDetailsViewModel.getTVShowDetails(currentTVShow.id).observe(this, Observer { response ->
            binding.pbTvShowDetails.visibility = View.GONE
            if (response != null) {
                loadImageSlider(response.tvShowDetails.pictures)
            }
        })
    }

    private fun loadImageSlider(sliderImages: List<String>) {
        binding.vpPictureSlider.adapter = ImageSliderAdapter(sliderImages)
        binding.vpPictureSlider.offscreenPageLimit = 1
        binding.vpPictureSlider.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicators(sliderImages.size)

        binding.vpPictureSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentImageIndicator(position)
            }
        })
    }

    private fun setupSliderIndicators(count: Int) {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)

        val indicators = Array(count) {
            val indicator = ImageView(applicationContext)
            indicator.setImageDrawable(ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.background_slider_indicator_inactive
                )
            )
            indicator.layoutParams = layoutParams
            binding.llSliderIndicators.addView(indicator)
            indicator
        }

        binding.llSliderIndicators.visibility = View.VISIBLE
        setCurrentImageIndicator(0)
    }

    private fun setCurrentImageIndicator(position: Int) {
        val childCount = binding.llSliderIndicators.childCount

        for (i in 0 until childCount) {
            val currentImage = binding.llSliderIndicators.getChildAt(i) as ImageView
            if (i == position) {
                currentImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                currentImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }
    }
}