package com.candybytes.taco.ui.fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.candybytes.taco.databinding.FragmentCategoryItemBinding
import com.candybytes.taco.ui.vm.category.CategoryItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryItemFragment : Fragment() {

    private val viewModel: CategoryItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCategoryItemBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@CategoryItemFragment.viewModel
            lifecycleOwner = this@CategoryItemFragment
        }.root
    }
}