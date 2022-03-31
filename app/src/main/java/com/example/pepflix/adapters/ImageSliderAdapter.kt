package com.example.pepflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pepflix.R
import com.example.pepflix.utilities.BindingAdapters

class ImageSliderAdapter(val sliderImages: List<String>) : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>(){


    inner class ImageSliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindSliderImage(imageURL: String) {
            val sliderImage = itemView.findViewById<ImageView>(R.id.ivSliderImage)
            BindingAdapters.setImageURL(sliderImage, imageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_slider_image,
            parent, false)

        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSliderImage(sliderImages[position])
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }
}