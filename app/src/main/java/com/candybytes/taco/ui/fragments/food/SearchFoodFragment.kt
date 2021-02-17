package com.candybytes.taco.ui.fragments.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.candybytes.taco.databinding.FragmentSearchFoodBinding
import com.candybytes.taco.ui.extensions.reObserve
import com.candybytes.taco.ui.fragments.BaseFragment
import com.candybytes.taco.ui.fragments.food.adapter.FoodDiffUtilCallback
import com.candybytes.taco.ui.fragments.food.adapter.SearchFoodAdapter
import com.candybytes.taco.ui.vm.MainViewModel
import com.candybytes.taco.ui.vm.food.SearchFoodViewModel
import com.candybytes.taco.vo.Food
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFoodFragment : BaseFragment<FragmentSearchFoodBinding>() {

    private val viewModel: SearchFoodViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val foodListObserver: Observer<List<Food>> by lazy {
        Observer<List<Food>> { food ->
            Timber.i("Food: $food")
            binding?.recyclerSearchFood?.adapter = SearchFoodAdapter(food) { foodId ->
                findNavController().navigate(
                    SearchFoodFragmentDirections.actionSearchFoodFragmentToFoodDetailFragment(foodId)
                )
            }
        }
    }

    private val searchFieldTextObserver: Observer<String> by lazy {
        Observer<String> { searchText ->
            Timber.i("Search text: $searchText")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSearchFoodBinding.inflate(layoutInflater, container, false).apply {
        viewModel = this@SearchFoodFragment.viewModel
        lifecycleOwner = this@SearchFoodFragment
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.textSearchField.reObserve(this, searchFieldTextObserver)
        mainViewModel.showSearchIcon.postValue(true)
        viewModel.food.reObserve(this, foodListObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mainViewModel.onHideSearch()
    }

}
