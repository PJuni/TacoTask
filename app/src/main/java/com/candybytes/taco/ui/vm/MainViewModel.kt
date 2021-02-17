package com.candybytes.taco.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.candybytes.taco.api.TacoService

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val showBottomBar = MutableLiveData(true)
    val showSearchField = MutableLiveData(false)
    val showSearchIcon = MutableLiveData(false)
    val textSearchField = MutableLiveData<String>()

    fun onSearchTextChanged(text: String) = textSearchField.postValue(text)

    fun onSearchIconClicked() {
        showSearchField.postValue(true)
        showSearchIcon.postValue(false)
    }

    fun onHideSearch() {
        showSearchField.postValue(false)
        showSearchIcon.postValue(false)
    }
}
