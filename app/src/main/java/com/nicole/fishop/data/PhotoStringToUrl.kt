package com.nicole.fishop.data

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.nicole.fishop.GlideApp
import com.nicole.fishop.R

object PhotoStringToUrl {

    @BindingAdapter("imageUrlWithCircleCrop")
    fun bindImageWithCircleCrop(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = it.toUri().buildUpon().build()
            GlideApp.with(imgView.context)
                .load(imgUri)
                .circleCrop()
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.fishileft)
                        .error(R.drawable.fish)
                )
                .into(imgView)
        }
    }


}