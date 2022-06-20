package com.nicole.fishop.fishSeller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.data.Category
import com.nicole.fishop.data.CategoryItem
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayBinding
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayItemBinding
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayItemItemBinding
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*

class FishSellerAddTodayAdapterItemItem() :
    ListAdapter<Category, RecyclerView.ViewHolder>(DiffCallback) {


    class RecordHolder(private var binding: FragmentFishSellerAddTodayItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.category = category
            binding.checkBox.text = category.items.toString()
            Logger.d("RecordHolder=> $category")
            binding.executePendingBindings()
        }

        @SuppressLint("SimpleDateFormat")
        fun getNow(): String {
            return if (android.os.Build.VERSION.SDK_INT >= 24) {
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())
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

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_RECORD = 0x00
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_RECORD -> RecordHolder(
                FragmentFishSellerAddTodayItemItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecordHolder -> {
                holder.bind((getItem(position) as Category))
            }
        }
    }
}


