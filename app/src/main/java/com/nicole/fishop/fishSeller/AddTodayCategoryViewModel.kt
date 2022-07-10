package com.nicole.fishop.fishSeller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddTodayCategoryViewModel(private val repository: FishopRepository) : ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status


    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error


    private var _fishAll = MutableLiveData<List<AddTodayItem>>()

    val fishAll: LiveData<List<AddTodayItem>>
        get() = _fishAll



//    var pushData = listOf<FishTodayCategory>()
    var fishToday= FishToday()

//    private var _fishTodayCategories = MutableLiveData<List<FishTodayCategory>>()
//
//    val fishTodayCategories: MutableLiveData<List<FishTodayCategory>>
//        get() = _fishTodayCategories

    var selectedColorPosition = MutableLiveData<Int>()

//    fun selectTodayCategory(fishTodayCategory: FishTodayCategory, position: Int) {
//        Logger.w("fishTodayCategory=$fishTodayCategory, position=$position")
//        _fishTodayCategories.value = listOf(fishTodayCategory)
//        selectedColorPosition.value = position
//    }

    var fishTodayCategories : MutableList<FishTodayCategory> = mutableListOf()
    /**
     * List<Category> -> List<AddTodayItem>
     */
    fun toCategoryName(categories: List<Category>): List<AddTodayItem> {
        Logger.d("toCategoryName")

        val newItems = mutableListOf<AddTodayItem>()

        for (category in categories) {

            val categoryName = AddTodayItem.CategoryName(category.categoryName)
            newItems.add(categoryName)

            val categoryItems = category.items

            for (item in categoryItems) {

                val categoryTitle = AddTodayItem.CategoryTitle(item.title)
                newItems.add(categoryTitle)

                val categoryChildItems = item.childItems

                for (items in categoryChildItems) {

                    val categoryChildItem = AddTodayItem.CategoryChildItem(items)
                    newItems.add(categoryChildItem)
                }


            }
        }

        return newItems
    }


    init {
        getFishAllResult()
    }

    fun setTodayFishRecord(TodayFishRecord: FishToday, Categories: List<FishTodayCategory>,User: Users){
        coroutineScope.launch {
            Logger.d("setTodayFishRecord")
            _status.value = LoadApiStatus.LOADING
            Logger.d("fishTodayCategories => $fishTodayCategories")
            when (val result = repository.setTodayFishRecord(TodayFishRecord,Categories,User)) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    Logger.d("success")
                }
                is Result1.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result1.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = FishopApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }



    fun getFishAllResult() {
        coroutineScope.launch {
            Logger.d("getFishAllResult")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getFishAll()
            Logger.d("repository.getFishAll()")
            Logger.d("result $result")

            _fishAll.value = when (result) {
                is Result1.Success -> {
                    _status.value = LoadApiStatus.DONE
                    toCategoryName(result.data)
                }
                is Result1.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result1.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = FishopApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false

        }

    }

}