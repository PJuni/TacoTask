package com.candybytes.taco.ui.fragments.food.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.candybytes.taco.BR
import com.candybytes.taco.R
import com.candybytes.taco.databinding.ItemFoodBinding
import com.candybytes.taco.ui.base.BaseViewAdapter
import com.candybytes.taco.ui.base.BaseViewHolder
import com.candybytes.taco.vo.Food


class SearchFoodAdapter(
    val food: List<Food>,
    val onFoodItemClicked: (Int) -> Unit
) : BaseViewAdapter<Food, SearchFoodViewHolder>(food) {

    override fun createViewHolder(inflatedView: View, viewType: Int) = SearchFoodViewHolder(
        inflatedView
    ).apply {
        itemView.setOnClickListener { onFoodItemClicked(food[adapterPosition].id) }
    }

    override fun getViewType(position: Int): Int = R.layout.item_food
}

class SearchFoodViewHolder(itemView: View) : BaseViewHolder<ItemFoodBinding, Food>(itemView) {
    override fun provideBindingId(): Int = BR.food
}

class FoodDiffUtilCallback(
    private val oldList: List<Food>,
    private val newList: List<Food>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
}