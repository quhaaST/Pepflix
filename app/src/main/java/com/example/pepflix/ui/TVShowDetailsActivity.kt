package com.example.pepflix.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pepflix.R
import com.example.pepflix.adapters.EpisodeAdapter
import com.example.pepflix.adapters.ImageSliderAdapter
import com.example.pepflix.databinding.ActivityTvshowDetailsBinding
import com.example.pepflix.databinding.LayoutEpisodesBottomSheetBinding
import com.example.pepflix.models.TVShow
import com.example.pepflix.models.TVShowDetails
import com.example.pepflix.utilities.BindingAdapters
import com.example.pepflix.utilities.TempDataHolder
import com.example.pepflix.viewmodels.TVShowDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvshowDetailsBinding
    private lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel
    private lateinit var episodesBottomSheetBinding: LayoutEpisodesBottomSheetBinding
    private lateinit var episodeBottomSheetDialog: BottomSheetDialog
    private lateinit var currentTVShow: TVShow
    private var isInWatchlist = false

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

        if (tvShowDetailsViewModel.getTVShowFromWatchlist(currentTVShow.id) != null) {
            isInWatchlist = true
            binding.ivWatchlist.setImageResource(R.drawable.ic_check)
        }
    }

    private fun getTVShowDetails() {
        binding.pbTvShowDetails.visibility = View.VISIBLE
        currentTVShow = intent.extras?.get("tvShow") as TVShow
        tvShowDetailsViewModel.getTVShowDetails(currentTVShow.id)
            .observe(this, Observer { response ->
                binding.pbTvShowDetails.visibility = View.GONE
                if (response != null) {
                    loadImageSlider(response.tvShowDetails.pictures)
                    BindingAdapters.setImageURL(
                        binding.rivTVShowImage,
                        response.tvShowDetails.imagePath
                    )

                    loadBasicTVShowDetails(currentTVShow)
                    loadDescription(response.tvShowDetails)
                    loadAdditionalTVShowDetails(response.tvShowDetails)

                    binding.btnWebsite.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(response.tvShowDetails.url)
                        startActivity(intent)
                    }

                    binding.btnEpisodes.visibility = View.VISIBLE
                    binding.btnWebsite.visibility = View.VISIBLE

                    // loading a bottom sheet dialog with episodes
                    binding.btnEpisodes.setOnClickListener {
                        episodeBottomSheetDialog = BottomSheetDialog(this)
                        val view = LayoutInflater.from(this).inflate(
                            R.layout.layout_episodes_bottom_sheet,
                            findViewById(R.id.llEpisodesContainer), false
                        )
                        episodesBottomSheetBinding = LayoutEpisodesBottomSheetBinding.bind(view)
                        episodeBottomSheetDialog.setContentView(episodesBottomSheetBinding.root)

                        episodesBottomSheetBinding.rvEpisodes.adapter =
                            EpisodeAdapter(response.tvShowDetails.episodes)
                        episodesBottomSheetBinding.tvTitle.text = "Episodes | ${currentTVShow.name}"

                        episodesBottomSheetBinding.ivClose.setOnClickListener {
                            episodeBottomSheetDialog.dismiss()
                        }

                        episodeBottomSheetDialog.show()
                    }

                    binding.ivWatchlist.setOnClickListener {
                        if (!isInWatchlist) {
                            tvShowDetailsViewModel.addToWatchlist(currentTVShow)
                            binding.ivWatchlist.setImageResource(R.drawable.ic_check)
                            Toast.makeText(applicationContext, "Added to watchlist", Toast.LENGTH_SHORT)
                                .show()
                            isInWatchlist = true
                        } else {
                            tvShowDetailsViewModel.removeTVShowFromWatchlist(currentTVShow)
                            binding.ivWatchlist.setImageResource(R.drawable.ic_watchlist)
                            Toast.makeText(this, "Removed from watchlist", Toast.LENGTH_SHORT).show()
                            isInWatchlist = false
                        }
                        TempDataHolder.IS_WATCHLIST_UPDATED = true
                    }
                    binding.ivWatchlist.visibility = View.VISIBLE
                }
            })
    }

    private fun loadAdditionalTVShowDetails(tvShowDetails: TVShowDetails) {
        binding.tvRating.text = "%.2f".format(tvShowDetails.rating.toDouble())

        if (tvShowDetails.genres.isNotEmpty()) {
            binding.tvGenres.text = tvShowDetails.genres.take(3).joinToString()
        } else {
            binding.tvGenres.text = "N/A"
        }

        binding.tvRuntime.text = "${tvShowDetails.runtime} Min"

        binding.llLayoutMisc.visibility = View.VISIBLE
        binding.vDivider.visibility = View.VISIBLE
        binding.vDivider2.visibility = View.VISIBLE
    }

    private fun loadImageSlider(sliderImages: List<String>) {
        binding.vpPictureSlider.adapter = ImageSliderAdapter(sliderImages)
        binding.vpPictureSlider.offscreenPageLimit = 1
        binding.vpPictureSlider.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicators(sliderImages.size)

        binding.vpPictureSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentImageIndicator(position)
            }
        })
    }

    private fun loadDescription(tvShowDetails: TVShowDetails) {
        binding.rivTVShowImage.visibility = View.VISIBLE
        binding.tvTVShowDescription.text = HtmlCompat.fromHtml(
            tvShowDetails.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.tvReadMore.setOnClickListener {
            binding.tvTVShowDescription.maxLines = Int.MAX_VALUE
            binding.tvTVShowDescription.ellipsize = null
            binding.tvReadMore.visibility = View.GONE
        }

        binding.tvTVShowDescription.visibility = View.VISIBLE
        binding.tvReadMore.visibility = View.VISIBLE
    }

    private fun setupSliderIndicators(count: Int) {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in 0 until count) {
            val indicator = ImageView(applicationContext)
            indicator.setImageDrawable(
                ContextCompat.getDrawable(
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