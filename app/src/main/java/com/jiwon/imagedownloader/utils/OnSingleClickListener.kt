package com.jiwon.imagedownloader.utils

import android.os.SystemClock
import android.view.View
import com.jiwon.imagedownloader.Constants

abstract class OnSingleClickListener : View.OnClickListener {
    private var mMinClickInterval: Long = Constants.SINGLE_CLICK_INTERVAL
    private var mLastClickTime: Long = 0
    abstract fun onSingleClick(v: View?)
    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= mMinClickInterval) {
            return
        }
        onSingleClick(v)
    }

    fun setClickInterval(interval: Long) {
        mMinClickInterval = interval
    }
}