package com.example.pepflix.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class BindingAdapters {

    companion object {
        fun setImageURL(imageView: ImageView, URL: String) {
            try {
                imageView.alpha = 0f
                Picasso.get().load(URL).noFade().into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.animate().setDuration(300).alpha(1f).start()
                    }

                    override fun onError(e: java.lang.Exception?) {

                    }
                })
            } catch (e: Exception) {}
        }
    }
}