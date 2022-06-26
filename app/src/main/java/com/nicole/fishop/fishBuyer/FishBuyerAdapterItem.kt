package com.nicole.fishop.fishBuyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.data.FishTodayCategory
import com.nicole.fishop.databinding.FragmentFishBuyerItemBinding
import com.nicole.fishop.databinding.FragmentFishBuyerItemItemBinding
import com.nicole.fishop.util.Logger

class FishBuyerAdapterItem : ListAdapter<FishTodayCategory, RecyclerView.ViewHolder>(DiffCallback) {

    class TodayFishHolder(private var binding: FragmentFishBuyerItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fishTodayCategory: FishTodayCategory) {
            binding.fishToday = fishTodayCategory
            binding.textViewFishCategory.text = fishTodayCategory.category
            Logger.d( "fishTodayCategory.category ${fishTodayCategory.category}")
            Logger.d( "binding.textViewFishCategory.text ${binding.textViewFishCategory.text}")
            binding.textViewPrice.text = fishTodayCategory.saleprice
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FishTodayCategory>() {
        override fun areItemsTheSame(oldItem: FishTodayCategory, newItem: FishTodayCategory): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FishTodayCategory, newItem: FishTodayCategory): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_RECORD = 0x00
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_RECORD -> TodayFishHolder(
                FragmentFishBuyerItemItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodayFishHolder -> {
                holder.bind((getItem(position) as FishTodayCategory))
            }
        }
    }
}

