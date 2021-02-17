package com.candybytes.taco.ui.vm.category

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.candybytes.taco.R
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.db.FoodDao
import com.candybytes.taco.ui.util.SourceIndex
import com.candybytes.taco.ui.util.TwoSourceMediatorData
import com.candybytes.taco.vo.Category
import timber.log.Timber
import java.util.*

class CategoryListViewModel @ViewModelInject constructor(
    private val tacoService: TacoService,
    private val foodDao: FoodDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var showSpinner = ObservableBoolean(true)
    var spinnerText = ObservableInt(R.string.spinner_text_foods)

    private val categoriesData by lazy {
        TwoSourceMediatorData<List<Category>>()
    }

    private val totalTacoCategories = liveData {
        spinnerText.set(R.string.spinner_text_categories)
        try {
            emit(tacoService.getAllCategoriesAsync())
        } catch (throwable: Throwable) {
            Timber.e(throwable)
        }
    }

    private val totalTacoFoods = liveData {
        val foodsDb = foodDao.getAllAsync()
        Timber.i("Foods in db exists: ${foodsDb.isNotEmpty()}")
        val finalFoods = if (foodsDb.isEmpty()) {
            tacoService.getAllFoodAsync().also { apiFoods ->
                apiFoods.forEach {
                    foodDao.insertAsync(it)
                }
            }
        } else foodsDb
        try {
            emit(finalFoods)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
        }
    }

    fun allCategories(): LiveData<List<Category>> {
        categoriesData.addSource(SourceIndex.First, totalTacoFoods) {
            it?.let { foods ->
                categoriesData.addSource(SourceIndex.Second, totalTacoCategories) {
                    it?.let { categories ->
                        categories.forEach { category ->
                            category.foods = foods.filter { it.categoryId == category.id }
                        }
                        showSpinner.set(false)
                        categoriesData.postValue(categories)
                    }
                }
            }
        }
        return categoriesData
    }

    companion object {
        private const val CATEGORY_KEY = "category"
    }
}
