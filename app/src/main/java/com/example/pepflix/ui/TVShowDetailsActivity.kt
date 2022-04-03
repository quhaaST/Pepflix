package com.example.pepflix.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pepflix.R
import com.example.pepflix.adapters.ImageSliderAdapter
import com.example.pepflix.databinding.ActivityTvshowDetailsBinding
import com.example.pepflix.models.TVShow
import com.example.pepflix.utilities.BindingAdapters
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

        binding.ivBackButton.setOnClickListener {
            onBackPressed()
        }

        getTVShowDetails()
    }

    private fun getTVShowDetails() {
        binding.pbTvShowDetails.visibility = View.VISIBLE
        val currentTVShow: TVShow = intent.extras?.get("tvShow") as TVShow
        tvShowDetailsViewModel.getTVShowDetails(currentTVShow.id).observe(this, Observer { response ->
            binding.pbTvShowDetails.visibility = View.GONE
            if (response != null) {
                loadImageSlider(response.tvShowDetails.pictures)
                BindingAdapters.setImageURL(binding.rivTVShowImage, response.tvShowDetails.imagePath)
                binding.rivTVShowImage.visibility = View.VISIBLE
                binding.tvTVShowDescription.text = HtmlCompat.fromHtml(
                    response.tvShowDetails.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

                binding.tvReadMore.setOnClickListener {
                    binding.tvTVShowDescription.maxLines = Int.MAX_VALUE
                    binding.tvTVShowDescription.ellipsize = null
                    binding.tvReadMore.visibility = View.GONE
                }

                binding.tvTVShowDescription.visibility = View.VISIBLE
                binding.tvReadMore.visibility = View.VISIBLE

                binding.tvRating.text = "%.2f".format(response.tvShowDetails.rating.toDouble())

                if (response.tvShowDetails.genres.isNotEmpty()) {
                    binding.tvGenres.text = response.tvShowDetails.genres.joinToString()
                } else {
                    binding.tvGenres.text = "N/A"
                }

                binding.tvRuntime.text = "${response.tvShowDetails.runtime} Min"

                binding.llLayoutMisc.visibility = View.VISIBLE
                binding.vDivider.visibility = View.VISIBLE
                binding.vDivider2.visibility = View.VISIBLE

                binding.btnWebsite.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(response.tvShowDetails.url)
                    startActivity(intent)
                }

                binding.btnEpisodes.visibility = View.VISIBLE
                binding.btnWebsite.visibility = View.VISIBLE

                loadBasicTVShowDetails(currentTVShow)
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

        for (i in 0 until count) {
            val indicator = ImageView(applicationContext)
            indicator.setImageDrawable(ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.background_slider_indicator_inactive
                )
            )
            indicator.layoutParams = layoutParams
            binding.llSliderIndicators.addView(indicator)
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

    private fun loadBasicTVShowDetails(tvShow: TVShow) {
        binding.tvTVShowTitle.text = tvShow.name
        binding.tvTVShowNetwork.text = tvShow.network
        binding.tvTVShowStatus.text = tvShow.status
        binding.tvTVShowStarted.text = "Started on: ${tvShow.startDate}"

        binding.tvTVShowTitle.visibility = View.VISIBLE
        binding.tvTVShowNetwork.visibility = View.VISIBLE
        binding.tvTVShowStarted.visibility = View.VISIBLE
        binding.tvTVShowStatus.visibility = View.VISIBLE
    }
}