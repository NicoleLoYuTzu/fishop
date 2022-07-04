package com.nicole.fishop

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.AddTodayItem
import com.nicole.fishop.fishSeller.AddTodayCategoryItemAdapter

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@BindingAdapter("AddTodayItem")
fun bindRecyclerViewWithHomeItems(recyclerView: RecyclerView, homeItems: List<AddTodayItem>?) {
    homeItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is AddTodayCategoryItemAdapter -> submitList(it)
            }
        }
    }
}
