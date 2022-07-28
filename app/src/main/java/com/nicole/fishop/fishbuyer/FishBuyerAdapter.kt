package com.nicole.fishop.fishbuyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.R
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
            binding.textViewSellername.text = fishToday.name
            binding.fishToday = fishToday
//            viewModel.getGoogleMapResult(fishToday.ownerId)

            binding.imageViewHeart.setOnClickListener {
                binding.imageViewHeart.setImageResource(R.drawable.heart)
                binding.imageViewHeart.setOnClickListener {
                    binding.imageViewHeart.setImageResource(R.drawable.like)
                }
            }

//            binding.imageViewHeart.setOnLikeListener(object : OnLikeListener {
//                override fun liked(likeButton: LikeButton) {
//
//                }
//                override fun unLiked(likeButton: LikeButton) {
//
//                }
//            })

            binding.textViewDistance.text = "${fishToday.distance / 1000}"

            binding.imageViewNavigate.setOnClickListener {
                onClickListener.onClick(fishToday)
                Logger.d("binding.imageViewNavigate fishToday $fishToday")
            }

            binding.imageViewChat.setOnClickListener {
                findNavController(binding.root).navigate(NavFragmentDirections.actionToChatBoxFragment(fishToday))
                Logger.i("FishBuyerAdapter fishToday $fishToday")
            }
//            binding.textViewChat.setOnClickListener {
//                findNavController(binding.root).navigate(NavFragmentDirections.actionToChatBoxFragment(fishToday))
//                Logger.i("FishBuyerAdapter fishToday $fishToday")
//            }
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
                holder.bind((getItem(position) as FishToday), onClickListener)
            }
        }
    }
}
