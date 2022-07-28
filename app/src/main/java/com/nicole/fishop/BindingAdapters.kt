package com.nicole.fishop

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Wayne Chen in Jul. 2019.
 */


@BindingAdapter("imageUrlTransform")
fun bindImageTransform(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .transform(CenterCrop(), RoundedCorners(25))
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.fishpress)
                    .error(R.drawable.fish)
                    .transform(CenterCrop(), RoundedCorners(25))
            )
            .into(imgView)
    }
}

@BindingAdapter("imageUrlWithCircleCrop")
fun bindImageWithCircleCrop(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
//            .circleCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.fishileft)
                    .error(R.drawable.fish)
            )
            .into(imgView)
    }
}
