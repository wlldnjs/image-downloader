package com.jiwon.imagedownloader.utils

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewConfiguration {
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mItemDecorationList: MutableList<RecyclerView.ItemDecoration>? = null
    private var mHasFixedSize: Boolean = true

    fun getLayoutManager(): RecyclerView.LayoutManager? {
        return mLayoutManager
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        mLayoutManager = layoutManager
    }

    fun getAdapter(): RecyclerView.Adapter<*>? {
        return mAdapter
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        mAdapter = adapter
    }

    fun getItemDecorationList(): List<RecyclerView.ItemDecoration>? {
        return mItemDecorationList
    }

    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
        if (mItemDecorationList == null) {
            mItemDecorationList = arrayListOf()
        }
        mItemDecorationList!!.add(itemDecoration)
    }

    fun isHasFixedSize(): Boolean {
        return mHasFixedSize
    }

    fun setHasFixedSize(hasFixedSize: Boolean) {
        mHasFixedSize = hasFixedSize
    }
}