package com.nicole.fishop.chatingroom.buyer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.R
import com.nicole.fishop.data.AddTodayItem
import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.databinding.*
import com.nicole.fishop.util.Logger

class BuyerChatAdapter() :
    ListAdapter<ChatRecord, RecyclerView.ViewHolder>(DiffCallback) {


    class MySideViewHolder(private var binding: FragmentChatBuyerMysideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            Logger.d("MySideViewHolder bind")

            binding.executePendingBindings()
        }
    }

    class TheOtherSideViewHolder(private var binding: FragmentChatBuyerTheOtherSideBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            Logger.d("TheOtherSideViewHolder bind")

            binding.executePendingBindings()
        }
    }



    companion object DiffCallback : DiffUtil.ItemCallback<ChatRecord>() {
        override fun areItemsTheSame(oldItem: ChatRecord, newItem: ChatRecord): Boolean {
            Logger.d("areItemsTheSame")
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatRecord, newItem: ChatRecord): Boolean {
            Logger.d("areContentsTheSame")
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_MYSIDE = 0x00
        private const val ITEM_VIEW_TYPE_THEOTHERSIDE = 0x01
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_MYSIDE -> MySideViewHolder(
                FragmentChatBuyerMysideBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_THEOTHERSIDE -> TheOtherSideViewHolder(
                FragmentChatBuyerTheOtherSideBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
//            is MySideViewHolder -> {
//                holder.bind((getItem(position) as ).category)
//            }
//            is TheOtherSideViewHolder -> {
//                holder.bind((getItem(position) as ).title)
//            }
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return when (getItem(position)) {
////            is AddTodayItem.CategoryName -> ITEM_VIEW_TYPE_CATEGORYNAME
////            is AddTodayItem.CategoryTitle -> ITEM_VIEW_TYPE_CATEGORYITEM
//        }
//    }
}
