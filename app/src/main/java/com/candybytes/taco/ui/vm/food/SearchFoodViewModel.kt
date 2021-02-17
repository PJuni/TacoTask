package com.candybytes.taco.ui.vm.food

import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.candybytes.taco.db.FoodDao
import timber.log.Timber

class SearchFoodViewModel @ViewModelInject constructor(
    private val foodDao: FoodDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var showSpinner = ObservableBoolean(true)

    val food = liveData {
        try {
            val food = foodDao.getAllAsync()
            showSpinner.set(false)
            emit(food)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
