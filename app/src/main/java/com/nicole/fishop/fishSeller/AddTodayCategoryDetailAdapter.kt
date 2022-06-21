package com.nicole.fishop.fishSeller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.CategoryItem
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayItemItemBinding
import com.nicole.fishop.util.Logger
import java.util.*

class AddTodayCategoryDetailAdapter() :
    ListAdapter<CategoryItem, RecyclerView.ViewHolder>(DiffCallback) {


    class RecordHolder(private var binding: FragmentFishSellerAddTodayItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.checkBox.text = item.title
//            binding.spinner2.adapter = adapter
            Logger.d("AddTodayCategoryDetailAdapter()=> $item")
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_RECORD = 0x00
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_RECORD -> RecordHolder(
                FragmentFishSellerAddTodayItemItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecordHolder -> {
                holder.bind((getItem(position) as CategoryItem))
            }
        }
    }
}


