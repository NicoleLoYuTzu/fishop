package com.nicole.fishop.chatingroom.chatbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.ChatBoxRecord
import com.nicole.fishop.data.ChatItem
import com.nicole.fishop.data.PhotoStringToUrl
import com.nicole.fishop.data.TimeChangFormat
import com.nicole.fishop.databinding.FragmentChatBuyerMysideBinding
import com.nicole.fishop.databinding.FragmentChatBuyerTheOtherSideBinding
import com.nicole.fishop.util.Logger

class ChatBoxAdapter :
    ListAdapter<ChatItem, RecyclerView.ViewHolder>(DiffCallback) {

    inner class MySideViewHolder(private var binding: FragmentChatBuyerMysideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRecord: ChatBoxRecord) {
            Logger.d("MySideViewHolder bind")

            binding.textViewChat.text = chatRecord.content
            binding.textViewTime.text = TimeChangFormat.getTime(chatRecord.time)
            PhotoStringToUrl.bindImageWithCircleCrop(binding.imageView6, chatRecord.senderphoto)

            Logger.d("binding.textViewChat.text ${binding.textViewChat.text}")

            binding.executePendingBindings()
        }
    }

    inner class TheOtherSideViewHolder(private var binding: FragmentChatBuyerTheOtherSideBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chatRecord: ChatBoxRecord) {
            Logger.d("TheOtherSideViewHolder bind")
            binding.textViewChat.text = chatRecord.content
            binding.textViewTime.text = TimeChangFormat.getTime(chatRecord.time)
            PhotoStringToUrl.bindImageWithCircleCrop(binding.imageViewPic, chatRecord.senderphoto)
            Logger.d("binding.textViewChat.text ${binding.textViewChat.text}")

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            Logger.d("areItemsTheSame")
            return oldItem.createdTime == newItem.createdTime
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            Logger.d("areContentsTheSame")
            return oldItem.createdTime == newItem.createdTime
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
            is MySideViewHolder -> {
                holder.bind((getItem(position) as ChatItem.MySide).chatRecord)
            }
            is TheOtherSideViewHolder -> {
                holder.bind((getItem(position) as ChatItem.TheOtherSide).chatRecord)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatItem.MySide -> ITEM_VIEW_TYPE_MYSIDE
            is ChatItem.TheOtherSide -> ITEM_VIEW_TYPE_THEOTHERSIDE
        }
    }
}
