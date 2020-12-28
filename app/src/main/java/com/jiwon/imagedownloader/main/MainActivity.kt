package com.jiwon.imagedownloader.main

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiwon.imagedownloader.Constants
import com.jiwon.imagedownloader.R
import com.jiwon.imagedownloader.adapter.ImageRecyclerViewAdapter
import com.jiwon.imagedownloader.databinding.ActivityMainBinding
import com.jiwon.imagedownloader.utils.RecyclerViewConfiguration
import java.io.File

class MainActivity : AppCompatActivity(), MainInterface {
    private val mBinding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main) }
    private val mViewModel by lazy { ViewModelProvider(this@MainActivity, MainViewModelFactory(this@MainActivity))[MainViewModel::class.java] }

    private val downloadManager by lazy { getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.run {
            lifecycleOwner = this@MainActivity
            viewModel = mViewModel
            imageRecyclerConfigure = setImageRecyclerViewConfigure()
        }
        mViewModel.getImageItems()

        val intentFilter = IntentFilter().apply { addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE) }
        registerReceiver(onDownloadComplete, intentFilter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_download, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val positions = (mBinding.imageRecyclerConfigure?.getAdapter() as? ImageRecyclerViewAdapter)?.getSelectedPositions()
        if (!positions.isNullOrEmpty()) {
            mViewModel.downloadImages(positions)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.compositeDisposable.dispose()
        unregisterReceiver(onDownloadComplete)
    }

    override fun showErrorMessage() {
        showToastMessage(getString(R.string.toast_error_message))
    }

    override fun clearSelected() {
        (mBinding.imageRecyclerConfigure?.getAdapter() as? ImageRecyclerViewAdapter)?.clearSelectedPositions()
    }

    override fun saveImageFile(url: String) {
        val fileName = url.substringAfterLast(Constants.FILE_SEPARATOR)
        val file = File(getExternalFilesDir(null), fileName)

        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationUri(Uri.fromFile(file))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        downloadManager.enqueue(request)
    }

    private val onDownloadComplete = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, Constants.INVALID_LONG_VALUE)
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                val query: DownloadManager.Query = DownloadManager.Query()
                query.setFilterById(id)
                val cursor = downloadManager.query(query)
                if (!cursor.moveToFirst()) {
                    return
                }
                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(columnIndex)
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    mViewModel.getDownloadCount.getAndAdd(1)
                    if (mViewModel.needDownloadCount.get() == mViewModel.getDownloadCount.get()) {
                        showToastMessage(getString(R.string.toast_complete_message, mViewModel.needDownloadCount.get()))
                    }
                } else if (status == DownloadManager.STATUS_FAILED) {
                    showErrorMessage()
                }
            }
        }
    }

    private fun showToastMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setImageRecyclerViewConfigure(): RecyclerViewConfiguration {
        return RecyclerViewConfiguration().apply {
            setAdapter(ImageRecyclerViewAdapter().apply {
                setMultiSelectedEnable(true)
            })
            setLayoutManager(LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false))
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }
}