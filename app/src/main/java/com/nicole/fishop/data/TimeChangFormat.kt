package com.nicole.fishop.data

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.nicole.fishop.GlideApp
import com.nicole.fishop.R
import java.text.SimpleDateFormat
import java.util.*

object TimeChangFormat {

    @SuppressLint("SimpleDateFormat")
    fun getTime(time: Long): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("HH:mm").format(time)
        } else {
            val tms = Calendar.getInstance()
            tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                tms.get(Calendar.MONTH).toString() + "/" +
                tms.get(Calendar.YEAR).toString() + " " +
                tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                tms.get(Calendar.MINUTE).toString() + ":" +
                tms.get(Calendar.SECOND).toString()
        }
    }

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
