package com.jiwon.imagedownloader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jiwon.imagedownloader.databinding.ItemImageInformationBinding
import com.jiwon.imagedownloader.model.ImageInfoModel
import com.jiwon.imagedownloader.utils.BaseRecyclerViewAdapter
import com.jiwon.imagedownloader.utils.BaseViewHolder

class ImageRecyclerViewAdapter : BaseRecyclerViewAdapter<ImageInfoModel.ImageItem, ImageRecyclerViewAdapter.ImageRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemImageInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageRecyclerViewHolder(binding)
    }

    override fun onBindView(holder: ImageRecyclerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, getSelectedPositions().contains(position))
        }
    }

    inner class ImageRecyclerViewHolder(private val binding: ItemImageInformationBinding) : BaseViewHolder<ImageInfoModel.ImageItem>(binding.root) {
        override fun bind(item: ImageInfoModel.ImageItem, selected: Boolean) {
            itemView.isSelected = selected
            binding.textTitle.text = item.title
            Glide.with(binding.imageThumbnail).load(item.thumbnail).into(binding.imageThumbnail)
            binding.imageCheck.visibility = if (item.isDownload) View.VISIBLE else View.GONE
        }
    }
}