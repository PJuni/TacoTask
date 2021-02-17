package com.candybytes.taco.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewAdapter <D, VH: BaseViewHolder<* ,D>>(
    data: List<D> = emptyList()
) : RecyclerView.Adapter<VH>() {

    protected abstract fun createViewHolder(inflatedView: View, viewType: Int): VH
    protected abstract fun getViewType(position: Int): Int

    var data: List<D> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        this.data = data
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(viewType, parent, false)

        return createViewHolder(inflatedView, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val element = data[position]
        holder.bind(element)
    }

    final override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItems(itemsToAdd: List<D>) {
        val changedData = this.data.toMutableList()
        changedData.addAll(itemsToAdd)
        this.data = changedData
    }
}