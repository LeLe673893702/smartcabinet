package com.llc.smartcabinet.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var itemData:T? = null
    open fun setData(itemData: T?) {
        this.itemData = itemData
    }

    fun getItemData():T? {
        return itemData
    }
}