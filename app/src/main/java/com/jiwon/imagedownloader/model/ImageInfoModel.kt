package com.jiwon.imagedownloader.model

class ImageInfoModel {
    data class ImageResult(
        val items: List<ImageItem>
    )

    data class ImageItem(
        val title: String,
        val thumbnail: String,
        val link: String,
        var isDownload: Boolean
    )
}
