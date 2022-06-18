package com.nicole.fishop.fishSeller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.databinding.FragmentFishBuyerItemBinding
import com.nicole.fishop.databinding.FragmentFishSellerItemBinding
import com.nicole.fishop.util.Logger
import java.sql.Timestamp


class FishSellerAdapterItem() :
    ListAdapter<FishRecord, RecyclerView.ViewHolder>(DiffCallback) {


    class RecordHolder(private var binding: FragmentFishSellerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fishRecord: FishRecord) {
            val time = Timestamp(fishRecord.time.time)
            binding.recyclerView.adapter = FishSellerAdapterItem()
            binding.textViewDate.text = time.toString()
            binding.fishRecord = fishRecord
            Logger.d("binding.fishRecord=> $fishRecord")
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FishRecord>() {
        override fun areItemsTheSame(oldItem: FishRecord, newItem: FishRecord): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FishRecord, newItem: FishRecord): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_RECORD = 0x00
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_RECORD -> RecordHolder(
                FragmentFishSellerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecordHolder -> {
                holder.bind((getItem(position) as FishRecord))
            }
        }
    }
}
}

