package com.nicole.fishop.fishBuyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.databinding.FragmentFishBuyerItemBinding

class FishBuyerAdapterItem : ListAdapter<FishToday, RecyclerView.ViewHolder>(DiffCallback) {

    class TodayFishHolder(private var binding: FragmentFishBuyerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fishToday: FishToday) {

            binding.fishToday = fishToday
            binding.RecyclerViewTodayfish.adapter = FishBuyerAdapterItem()

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FishToday>() {
        override fun areItemsTheSame(oldItem: FishToday, newItem: FishToday): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FishToday, newItem: FishToday): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_RECORD = 0x00
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_RECORD -> TodayFishHolder(
                FragmentFishBuyerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodayFishHolder -> {
                holder.bind((getItem(position) as FishToday))
            }
        }
    }
}

