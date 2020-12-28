package com.jiwon.imagedownloader.network

import com.jiwon.imagedownloader.Constants

class ImageRequestItem(private val query: String) {
    private val mDisplay = Constants.DEFAULT_COUNT
    private val mFilter = Constants.DEFAULT_IMAGE_SIZE

    fun getParameters(): Map<String, String> {
        return HashMap<String, String>().apply {
            this["query"] = query
            this["display"] = mDisplay
            this["filter"] = mFilter
        }
    }

}