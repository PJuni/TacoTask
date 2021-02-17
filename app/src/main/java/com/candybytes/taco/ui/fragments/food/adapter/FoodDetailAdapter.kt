package com.candybytes.taco.ui.fragments.food.adapter

import android.view.View
import com.candybytes.taco.BR
import com.candybytes.taco.R
import com.candybytes.taco.databinding.ItemCategoryBinding
import com.candybytes.taco.databinding.ItemFoodBinding
import com.candybytes.taco.ui.base.BaseViewAdapter
import com.candybytes.taco.ui.base.BaseViewHolder
import com.candybytes.taco.ui.fragments.category.adapter.CategoriesViewHolder
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import com.candybytes.taco.vo.Nutrient

class FoodDetailAdapter(
    val nutrients: List<Pair<String, Nutrient>>
) : BaseViewAdapter<Pair<String, Nutrient>, FoodDetailViewHolder>(nutrients) {

    override fun createViewHolder(inflatedView: View, viewType: Int) = FoodDetailViewHolder(
        inflatedView
    )
    override fun getViewType(position: Int): Int = R.layout.item_food_details
}

class FoodDetailViewHolder(itemView: View) : BaseViewHolder<ItemFoodBinding, Pair<String, Nutrient>>(itemView) {
    override fun provideBindingId(): Int = BR.nutrients
}