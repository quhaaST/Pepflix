package com.example.pepflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pepflix.R
import com.example.pepflix.databinding.ItemContainerTvShowBinding
import com.example.pepflix.listeners.TVShowListener
import com.example.pepflix.listeners.WatchlistListener
import com.example.pepflix.models.TVShow
import com.example.pepflix.utilities.BindingAdapters

class WatchlistAdapter(val tvShows: List<TVShow>, val watchlistListener: WatchlistListener)
    : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    inner class WatchlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemContainerTvShowBinding.bind(view)

        fun bindTVShow(tvShow: TVShow) {
            BindingAdapters.setImageURL(binding.imageTvShow, tvShow.thumbnail)
            binding.tvShowTitle.text = tvShow.name
            binding.tvTextNetwork.text = "${tvShow.network} (${tvShow.country})"
            binding.tvTextStarted.text = tvShow.startDate
            binding.tvTextStatus.text = tvShow.status

            itemView.setOnClickListener {
                watchlistListener.onTVShowClicked(tvShow)
            }

            binding.ivDelete.setOnClickListener {
                watchlistListener.removeTVShowFromWatchlist(tvShow, adapterPosition)
            }

            binding.ivDelete.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_tv_show, parent, false)
        return WatchlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bindTVShow(tvShows[position])
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
}