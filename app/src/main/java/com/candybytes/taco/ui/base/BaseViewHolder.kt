package com.candybytes.taco.ui.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<out B : ViewDataBinding, in D>(view: View) : RecyclerView.ViewHolder(view) {

    protected val binding: B = DataBindingUtil.bind(view) ?: throw NullPointerException("The provided view is not a data binding layout")

    abstract fun provideBindingId(): Int

    fun bind(data: D) {
        val bindingId: Int = provideBindingId()
        update(data)
        binding.setVariable(bindingId, data)
        binding.executePendingBindings()
    }

    open fun update(data: D) {
        // Do nothing
    }

}