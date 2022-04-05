package com.example.pepflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pepflix.R
import com.example.pepflix.databinding.ItemContainerEpisodeBinding
import com.example.pepflix.models.Episode

class EpisodeAdapter(val episodes: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {
    inner class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var binding = ItemContainerEpisodeBinding.bind(view)

        fun bindEpisode(episode: Episode) {
            val episodeTitle = "S${if (episode.season < 10) "0" else ""}${episode.season} E${if (episode.episode < 10) "0" else ""}${episode.episode}"
            binding.tvEpisodeTitle.text = episodeTitle
            binding.tvEpisodeName.text = episode.title
            binding.tvEpisodeAirDate.text = "Air Date: ${episode.airDate}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_episode,
            parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindEpisode(episodes[position])
    }

    override fun getItemCount(): Int {
        return episodes.size
    }
}