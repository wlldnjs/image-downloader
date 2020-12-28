package com.jiwon.imagedownloader.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiwon.imagedownloader.Constants
import com.jiwon.imagedownloader.model.ImageInfoModel
import com.jiwon.imagedownloader.network.ImageAPI
import com.jiwon.imagedownloader.network.ImageRequestItem
import com.jiwon.imagedownloader.network.RetrofitManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger


class MainViewModel(private val mMainInterface: MainInterface) : ViewModel() {
    val imageItems = MutableLiveData<List<ImageInfoModel.ImageItem>>()
    val compositeDisposable = CompositeDisposable()
    var needDownloadCount = AtomicInteger(Constants.DEFAULT_INT_VALUE)
    var getDownloadCount = AtomicInteger(Constants.DEFAULT_INT_VALUE)

    private val mImageApi = RetrofitManager.getRetrofit().create(ImageAPI::class.java)

    fun downloadImages(selectedPositions: MutableList<Int>) {
        var downloadCount = Constants.DEFAULT_INT_VALUE
        getDownloadCount.set(Constants.DEFAULT_INT_VALUE)
        val items = imageItems.value
        selectedPositions.forEach {
            if (items?.get(it)?.isDownload == false) {
                downloadCount++
                downloadImageFile(items[it].link)
                items[it].isDownload = true
            }
        }
        needDownloadCount.set(downloadCount)
        imageItems.postValue(items)
        mMainInterface.clearSelected()
    }


    fun getImageItems() {
        compositeDisposable.add(
            mImageApi.getImages(ImageRequestItem(Constants.DEFAULT_QUERY).getParameters())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe({
                    imageItems.postValue(it.items)
                }, {
                    mMainInterface.showErrorMessage()
                    it.printStackTrace()
                })
        )
    }

    private fun downloadImageFile(url: String) {
        mMainInterface.saveImageFile(url)
    }
}