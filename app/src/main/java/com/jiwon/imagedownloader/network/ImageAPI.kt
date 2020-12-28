package com.jiwon.imagedownloader.network

import com.jiwon.imagedownloader.model.ImageInfoModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ImageAPI {
    @GET("/v1/search/image")
    fun getImages(@QueryMap parameters: Map<String, String>): Single<ImageInfoModel.ImageResult>

}