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
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ChatRecord, RecyclerView.ViewHolder>(DiffCallback) {


    class OnClickListener(val clickListener: (chatRecord: ChatRecord) -> Unit) {
        fun onClick(chatRecord: ChatRecord) = clickListener(chatRecord)
    }

    class RecordHolder(private var binding: FragmentChatRecordBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatRecord: ChatRecord,onClickListener: OnClickListener) {

            binding.root.setOnClickListener { onClickListener.onClick(chatRecord) }

            if (UserManager.user?.accountType == "buyer") {
                binding.textViewName.text = chatRecord.salerName
                TimeChangFormat.bindImageWithCircleCrop(binding.imageView6,chatRecord.salerPhoto)
            }else if(UserManager.user?.accountType == "saler"){
                binding.textViewName.text = chatRecord.buyerName
                TimeChangFormat.bindImageWithCircleCrop(binding.imageView6, chatRecord.buyerPhoto)
            }
            binding.textViewContext.text = chatRecord.lastchat

            Logger.i("chatRecord => ${chatRecord}")


            try {
                binding.textViewTime.text= TimeChangFormat.getTime(chatRecord.lastchatTime.toLong())
            }catch (e:Exception){

            }

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
                holder.bind((getItem(position) as ChatRecord), onClickListener)
            }
        }
    }
}