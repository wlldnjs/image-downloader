package com.jiwon.imagedownloader.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jiwon.imagedownloader.model.ImageInfoModel
import com.jiwon.imagedownloader.utils.RecyclerViewConfiguration

@BindingAdapter("configuration")
fun configureRecyclerView(recyclerView: RecyclerView, configuration: RecyclerViewConfiguration?) {
    configuration?.run {
        recyclerView.layoutManager = getLayoutManager()
        recyclerView.adapter = getAdapter()
        getItemDecorationList()?.forEach {
            recyclerView.addItemDecoration(it)
        }
        recyclerView.setHasFixedSize(isHasFixedSize())
    }
}

@BindingAdapter("imageItems")
fun setImageItems(recyclerView: RecyclerView, items: MutableList<ImageInfoModel.ImageItem>?) {
    items?.let {
        (recyclerView.adapter as? ImageRecyclerViewAdapter)?.setItems(it)
    }
}