package com.candybytes.taco.ui.fragments.category.adapter

import android.view.View
import com.candybytes.taco.BR
import com.candybytes.taco.R
import com.candybytes.taco.databinding.ItemCategoryBinding
import com.candybytes.taco.ui.base.BaseViewAdapter
import com.candybytes.taco.ui.base.BaseViewHolder
import com.candybytes.taco.vo.Category

class CategoriesAdapter(
    categories: List<Category>
) : BaseViewAdapter<Category, CategoriesViewHolder>(categories) {

    override fun createViewHolder(inflatedView: View, viewType: Int) = CategoriesViewHolder(
        inflatedView
    )
    override fun getViewType(position: Int): Int = R.layout.item_category
}

class CategoriesViewHolder(itemView: View) : BaseViewHolder<ItemCategoryBinding, Category>(itemView) {
    override fun provideBindingId(): Int = BR.category
}