package com.jiwon.imagedownloader.utils

import androidx.recyclerview.widget.RecyclerView
import com.jiwon.imagedownloader.Constants

abstract class BaseRecyclerViewAdapter<T, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemList: MutableList<T>? = null

    private var mIsSelectedEnable: Boolean = false
    private var mSelectedPosition = Constants.INVALID_INDEX

    private var mIsMultiSelectedEnable: Boolean = false
    private var mSelectedPositions: MutableList<Int> = arrayListOf()

    protected abstract fun onBindView(holder: H, position: Int)

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { view ->
            val layoutPosition = holder.layoutPosition
            if (mIsSelectedEnable) {
                mSelectedPosition = layoutPosition
            } else if (mIsMultiSelectedEnable) {
                if (mSelectedPositions.contains(layoutPosition)) {
                    // unselected
                    mSelectedPositions.remove(layoutPosition)
                    holder.itemView.isSelected = false
                } else {
                    // selected
                    mSelectedPositions.add(layoutPosition)
                    holder.itemView.isSelected = true
                }
            }
        }
        @Suppress("UNCHECKED_CAST")
        onBindView(holder as H, position)
    }

    fun getItem(position: Int): T? {
        return mItemList?.get(position)
    }

    fun getItems(): MutableList<T>? {
        return mItemList
    }

    fun setItems(items: MutableList<T>) {
        mItemList = items
        notifyDataSetChanged()
    }

    fun getSelectedPosition(): Int {
        return mSelectedPosition
    }

    fun setSelectedPosition(position: Int) {
        mSelectedPosition = position
    }

    fun setMultiSelectedEnable(isSelectedEnable: Boolean) {
        mIsMultiSelectedEnable = isSelectedEnable
    }

    fun getSelectedPositions(): MutableList<Int> {
        return mSelectedPositions
    }

    fun setSelectedPositions(positions: MutableList<Int>) {
        mSelectedPositions = positions
        notifyDataSetChanged()
    }

    fun clearSelectedPositions() {
        mSelectedPositions.clear()
        notifyDataSetChanged()
    }
}