package com.candybytes.taco.ui.vm.food

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.candybytes.taco.db.FoodDao

class FoodDetailViewModel @ViewModelInject constructor(
    private val foodDao: FoodDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val chooseImageClicked = MutableLiveData(false)

    fun getFoodBy(id: Int) = liveData {
        val food = foodDao.getBy(id)
        val nutrients = food.nutrients.map {
            Pair(it.key, it.value)
        }
        emit(nutrients)
    }
}
