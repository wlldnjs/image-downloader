package com.jiwon.imagedownloader.main

interface MainInterface {
    fun showErrorMessage()

    fun clearSelected()

    fun saveImageFile(url: String)
}