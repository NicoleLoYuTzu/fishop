package com.nicole.fishop.fishBuyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.databinding.FragmentFishBuyerItemBinding
import com.nicole.fishop.util.Logger

class FishBuyerAdapter(private val onClickListener: OnClickListener) : ListAdapter<FishToday, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (fishToday: FishToday) -> Unit) {
        fun onClick(fishToday: FishToday) = clickListener(fishToday)
    }

    class TodayFishHolder(private var binding: FragmentFishBuyerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fishToday: FishToday, onClickListener: OnClickListener) {
            binding.textViewSellername.text =fishToday.name
            binding.fishToday = fishToday
//            binding.imageViewNavigate.setOnClickListener {
//                findNavController(binding.root).navigate(NavFragmentDirections.actionToFishBuyerGoogleMap())
//            }
            binding.imageViewNavigate.setOnClickListener { onClickListener.onClick(fishToday) }

            binding.imageViewChat.setOnClickListener {
                findNavController(binding.root).navigate(NavFragmentDirections.actionToBuyerChatFragment())
            }
            binding.textViewChat.setOnClickListener {
                findNavController(binding.root).navigate(NavFragmentDirections.actionToSalerChatFragment())
            }
            binding.RecyclerViewTodayfish.adapter = FishBuyerAdapterItem()
            (binding.RecyclerViewTodayfish.adapter as FishBuyerAdapterItem).submitList(fishToday.category)
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
                holder.bind((getItem(position) as FishToday),onClickListener)
            }
        }
    }
}

