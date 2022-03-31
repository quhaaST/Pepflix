package com.example.pepflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pepflix.R
import com.example.pepflix.databinding.ItemContainerTvShowBinding
import com.example.pepflix.listeners.TVShowListener
import com.example.pepflix.models.TVShow
import com.example.pepflix.utilities.BindingAdapters

class TVShowAdapter(val tvShows: List<TVShow>, val tvShowListener: TVShowListener)
    : RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {

    inner class TVShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTVShow(tvShow: TVShow) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageTvShow)
            val title = itemView.findViewById<TextView>(R.id.tvShowTitle)
            val network = itemView.findViewById<TextView>(R.id.tvTextNetwork)
            val started = itemView.findViewById<TextView>(R.id.tvTextStarted)
            val status = itemView.findViewById<TextView>(R.id.tvTextStatus)

            BindingAdapters.setImageURL(imageView, tvShow.thumbnail)
            title.text = tvShow.name
            network.text = "${tvShow.network} (${tvShow.country})"
            started.text = tvShow.startDate
            status.text = tvShow.status

            itemView.setOnClickListener {
                tvShowListener.onTVShowClicked(tvShow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bindTVShow(tvShows[position])
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
}