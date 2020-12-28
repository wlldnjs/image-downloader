package com.jiwon.imagedownloader.network

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface ImageDownloadAPI {
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String?): Observable<Response<ResponseBody?>?>?
}