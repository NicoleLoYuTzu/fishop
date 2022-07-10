package com.nicole.fishop.chatingroom.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.TimeChangFormat
import com.nicole.fishop.databinding.*
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter() :
    ListAdapter<ChatRecord, RecyclerView.ViewHolder>(DiffCallback) {

    class RecordHolder(private var binding: FragmentChatRecordBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatRecord: ChatRecord) {

            binding.textViewName.text = chatRecord.salerName
            binding.textViewContext.text = chatRecord.lastchat
            TimeChangFormat.bindImageWithCircleCrop(binding.imageView6,chatRecord.salerPhoto)
            Logger.i("chatRecord => ${chatRecord}")


            binding.textViewTime.text= TimeChangFormat.getTime(chatRecord.lastchatTime.toLong())
            Logger.i("binding.textViewTime.text => ${binding.textViewTime.text}")
            Logger.i("$chatRecord")


            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatRecord>() {
        override fun areItemsTheSame(oldItem: ChatRecord, newItem: ChatRecord): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ChatRecord, newItem: ChatRecord): Boolean {
            return oldItem.id == newItem.id
        }
        private const val ITEM_VIEW_TYPE_RECORD = 0x00
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_RECORD -> RecordHolder(FragmentChatRecordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecordHolder -> {
                holder.bind((getItem(position) as ChatRecord))
            }
        }
    }
}