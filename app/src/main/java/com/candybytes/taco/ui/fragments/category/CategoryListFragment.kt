package com.candybytes.taco.ui.fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.candybytes.taco.databinding.FragmentCategoryListBinding
import com.candybytes.taco.ui.extensions.reObserve
import com.candybytes.taco.ui.fragments.BaseFragment
import com.candybytes.taco.ui.fragments.category.adapter.CategoriesAdapter
import com.candybytes.taco.ui.vm.category.CategoryListViewModel
import com.candybytes.taco.vo.Category
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CategoryListFragment : BaseFragment<FragmentCategoryListBinding>() {

    private val viewModel: CategoryListViewModel by viewModels()

    private val categoryListObserver: Observer<List<Category>> by lazy {
        Observer<List<Category>> { categories ->
            Timber.i("Categories: $categories")
            binding?.recyclerCategoryList?.adapter = CategoriesAdapter(categories)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCategoryListBinding.inflate(
        layoutInflater,
        container,
        false
    ).apply {
        viewModel = this@CategoryListFragment.viewModel
        lifecycleOwner = this@CategoryListFragment
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allCategories().reObserve(this, categoryListObserver)
    }
}
