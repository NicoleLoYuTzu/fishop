package com.nicole.fishop.fishseller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.databinding.ActivityMainBinding.inflate
import com.nicole.fishop.databinding.FragmentChatBuyerBinding.inflate
import com.nicole.fishop.databinding.FragmentFishSellerItemBinding
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*

class FishSellerAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<FishRecord, RecyclerView.ViewHolder>(
        DiffCallback
    ) {

    class OnClickListener(val clickListener: (fishRecord: FishRecord) -> Unit) {
        fun onClick(fishRecord: FishRecord) = clickListener(fishRecord)
    }

    // 把賣家每天的紀錄都抓出來
    class RecordHolder(private var binding: FragmentFishSellerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fishRecord: FishRecord, onClickListener: OnClickListener) {
            // date的屬性time就是毫秒
            val time = getNowTime(fishRecord.time.time)
            val date = getNowDate(fishRecord.time.time)
            binding.recyclerView.adapter = FishSellerAdapterItem()
            val fishCategory = fishRecord.fishCategory
            (binding.recyclerView.adapter as FishSellerAdapterItem).submitList(fishCategory)
            binding.textViewDate.text = date
            binding.textViewTime.text = time
            binding.fishRecord = fishRecord
            Logger.d("binding.fishRecord => $fishRecord")
            binding.root.setOnClickListener { onClickListener.onClick(fishRecord) }
            binding.executePendingBindings()
        }

        @SuppressLint("SimpleDateFormat")
        private fun getNowTime(time: Long): String {
            return if (android.os.Build.VERSION.SDK_INT >= 24) {
                SimpleDateFormat("HH:mm").format(time)
            } else {
                val tms = Calendar.getInstance()
                tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                    tms.get(Calendar.MONTH).toString() + "/" +
                    tms.get(Calendar.YEAR).toString() + " " +
                    tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                    tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    tms.get(Calendar.MINUTE).toString() + ":" +
                    tms.get(Calendar.SECOND).toString()
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun getNowDate(time: Long): String {
            return if (android.os.Build.VERSION.SDK_INT >= 24) {
                SimpleDateFormat("yyyy/MM/dd").format(time)
            } else {
                val tms = Calendar.getInstance()
                tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                    tms.get(Calendar.MONTH).toString() + "/" +
                    tms.get(Calendar.YEAR).toString() + " " +
                    tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                    tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    tms.get(Calendar.MINUTE).toString() + ":" +
                    tms.get(Calendar.SECOND).toString()
            }
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
                holder.bind((getItem(position) as FishRecord), onClickListener)
            }
        }
    }
}
