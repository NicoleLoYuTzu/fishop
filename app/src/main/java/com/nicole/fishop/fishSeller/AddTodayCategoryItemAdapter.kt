package com.nicole.fishop.fishSeller

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.R
import com.nicole.fishop.data.AddTodayItem
import com.nicole.fishop.databinding.*
import com.nicole.fishop.util.Logger


class AddTodayCategoryItemAdapter() :
    ListAdapter<AddTodayItem, RecyclerView.ViewHolder>(DiffCallback) {


    class CategoryNameViewHolder(private var binding: FragmentFishSellerAddTodayCategorynameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            Logger.d("CategoryNameViewHolder bind")
            binding.textCategory.text = category
            binding.imageView4.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.fishiright))
            binding.imageView5.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.fishileft))
            Logger.d("CategoryNameViewHolder => $category")
            binding.category = category
            binding.executePendingBindings()
        }
    }

    class CategoryItemViewHolder(private var binding: FragmentFishSellerAddTodayItemItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            Logger.d("CategoryItemViewHolder bind")
            binding.title = title
            binding.checkBox.text = title
//            binding.checkBox.setOnCheckedChangeListener(
//
//            )
            val adapter = ArrayAdapter.createFromResource(binding.root.context, R.array.unit, R.layout.simple_spinner_dropdown_item)
            binding.spinner2.adapter = adapter
            binding.executePendingBindings()
        }
    }

    class CategoryChildItemViewHolder(private var binding: FragmentFishSellerAddTodayItemChilditemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            Logger.d("CategoryItemViewHolder bind")
            binding.title = title
            binding.textViewChilditem.text = title
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<AddTodayItem>() {
        override fun areItemsTheSame(oldItem: AddTodayItem, newItem: AddTodayItem): Boolean {
            Logger.d("areItemsTheSame")
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AddTodayItem, newItem: AddTodayItem): Boolean {
            Logger.d("areContentsTheSame")
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_CATEGORYNAME = 0x00
        private const val ITEM_VIEW_TYPE_CATEGORYITEM = 0x01
        private const val ITEM_VIEW_TYPE_CATEGORYCHILDITEM = 0x02
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_CATEGORYNAME -> CategoryNameViewHolder(
                FragmentFishSellerAddTodayCategorynameBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_CATEGORYITEM -> CategoryItemViewHolder(
                FragmentFishSellerAddTodayItemItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_CATEGORYCHILDITEM-> CategoryChildItemViewHolder(
                FragmentFishSellerAddTodayItemChilditemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryNameViewHolder -> {
                holder.bind((getItem(position) as AddTodayItem.CategoryName).category)
            }
            is CategoryItemViewHolder -> {
                holder.bind((getItem(position) as AddTodayItem.CategoryTitle).title)
            }
            is CategoryChildItemViewHolder -> {
                holder.bind((getItem(position) as AddTodayItem.CategoryChildItem).childItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AddTodayItem.CategoryName -> ITEM_VIEW_TYPE_CATEGORYNAME
            is AddTodayItem.CategoryTitle -> ITEM_VIEW_TYPE_CATEGORYITEM
            is AddTodayItem.CategoryChildItem ->ITEM_VIEW_TYPE_CATEGORYCHILDITEM
        }
    }
}


