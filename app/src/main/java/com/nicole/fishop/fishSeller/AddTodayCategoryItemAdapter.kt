package com.nicole.fishop.fishSeller

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.fishop.R
import com.nicole.fishop.data.AddTodayItem
import com.nicole.fishop.data.FishTodayCategory
import com.nicole.fishop.databinding.*
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.util.*


class AddTodayCategoryItemAdapter(private var categoryViewModel: AddTodayCategoryViewModel) :
    ListAdapter<AddTodayItem, RecyclerView.ViewHolder>(DiffCallback) {

    var checkedItems = SparseBooleanArray()
    var editPriceItems = SparseBooleanArray()
    var editAmountItems = SparseBooleanArray()

    inner class CategoryNameViewHolder(
        private var binding: FragmentFishSellerAddTodayCategorynameBinding,
        private var categoryViewModel: AddTodayCategoryViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            Logger.d("CategoryNameViewHolder bind")
            binding.textCategory.text = category
            binding.imageView4.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.fishiright
                )
            )
            binding.imageView5.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.fishileft
                )
            )
            Logger.d("CategoryNameViewHolder => $category")
            binding.category = category
            binding.executePendingBindings()
        }
    }

    inner class CategoryItemViewHolder(
        var binding: FragmentFishSellerAddTodayItemItemBinding,
        private var viewModel: AddTodayCategoryViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.checkBox.setOnClickListener {
                if (!checkedItems.get(adapterPosition, false)) {//checkbox checked
                    binding.checkBox.isChecked = true
                    checkedItems.put(adapterPosition, true)
                    Logger.d("checkedItems $checkedItems")
                    Logger.d("binding.checkBox.isChecked.toString() ${binding.checkBox.text}")
                    val a = FishTodayCategory(
                        "",
                        binding.editTextUnit.text.toString(),
                        binding.checkBox.text.toString(),
                        binding.editTextPrice.text.toString(),
                        binding.spinner2.selectedItem.toString(),
                        "",
                        "",
                        ""
                    )
//                   for (i in viewModel.fishTodayCategories.value!!){
//                       i.category = binding.checkBox.text.toString()
//                   }
                    viewModel.fishTodayCategories.add(a)
                    Logger.i("binding.checkBox.setOnClickListener a=> ${a}")
                    Logger.i("viewModel.fishTodayCategories data add${viewModel.fishTodayCategories}")
                } else {
                    binding.checkBox.isChecked = false
                    checkedItems.put(adapterPosition, false)
                    val a = FishTodayCategory(
                        "",
                        binding.editTextUnit.text.toString(),
                        binding.checkBox.text.toString(),
                        binding.editTextPrice.text.toString(),
                        binding.spinner2.selectedItem.toString(),
                        "",
                        "",
                        ""
                    )
                    viewModel.fishTodayCategories.remove(a)
                    Logger.i("viewModel.fishTodayCategories data remove${viewModel.fishTodayCategories}")
                }
            }
            val editTextPriceTextWatcher = MyTextWatcher(binding.editTextPrice)
            val editTextAmountTextWatcher = MyTextWatcher(binding.editTextUnit)
            binding.editTextPrice.addTextChangedListener(editTextPriceTextWatcher)
            binding.editTextUnit.addTextChangedListener(editTextAmountTextWatcher)

            val spinner = MySpinner(binding.spinner2)
            binding.spinner2.onItemSelectedListener = spinner
        }

        inner class MySpinner(private val spinner: Spinner) : AdapterView.OnItemSelectedListener {
            //                for (i in viewModel.fishTodayCategories) {
//
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                for (i in viewModel.fishTodayCategories) {
                    if (i.category == binding.checkBox.text.toString()) {
                        i.unit = binding.spinner2.selectedItem.toString()
                    }
                }
                Logger.d("MySpinner viewModel.fishTodayCategories => ${viewModel.fishTodayCategories}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        inner class MyTextWatcher(private val editText: EditText) : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {

                for (i in viewModel.fishTodayCategories) {
                    if (i.category == binding.checkBox.text.toString() && binding.checkBox.isChecked) {
                        i.saleprice = binding.editTextPrice.text.toString()
                        i.amount = binding.editTextUnit.text.toString()
                    }
                }

                Logger.d("MyTextWatcher viewModel.fishTodayCategories => ${viewModel.fishTodayCategories}")
            }
        }


        fun bind(title: String) {
            Logger.d("CategoryItemViewHolder bind")
            binding.title = title
            binding.checkBox.text = title
            val adapter = ArrayAdapter.createFromResource(
                binding.root.context,
                R.array.unit,
                R.layout.simple_spinner_dropdown_item
            )

            binding.spinner2.adapter = adapter
            binding.executePendingBindings()
        }
    }

    inner class CategoryChildItemViewHolder(private var binding: FragmentFishSellerAddTodayItemChilditemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //暫時沒用到
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
                ), categoryViewModel
            )
            ITEM_VIEW_TYPE_CATEGORYITEM -> CategoryItemViewHolder(
                FragmentFishSellerAddTodayItemItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), categoryViewModel
            )
            ITEM_VIEW_TYPE_CATEGORYCHILDITEM -> CategoryChildItemViewHolder(
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
                holder.bind((getItem(position) as AddTodayItem.CategoryName).categoryName)
            }
            is CategoryItemViewHolder -> {
                holder.bind((getItem(position) as AddTodayItem.CategoryTitle).title)
                holder.binding.checkBox.isChecked = checkedItems.get(position, false)
                holder.binding.editTextPrice.tag = position;
                holder.binding.editTextUnit.tag = position;
                holder.binding.spinner2.tag = position

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
            is AddTodayItem.CategoryChildItem -> ITEM_VIEW_TYPE_CATEGORYCHILDITEM
            else -> throw ClassCastException("Unknown viewType ${getItem(position)}")
        }
    }

}


