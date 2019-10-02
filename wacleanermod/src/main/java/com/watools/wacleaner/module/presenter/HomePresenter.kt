package com.watools.wacleaner.module.presenter

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.utility.WACleanerConstants
import com.watools.wacleaner.module.utility.WAClenerUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class HomePresenter(val context: Context) {

    var fileCount = 0

    fun getWATotalSize(): String {
        var totalSize = 0L
        var filesDirectory = File(Environment.getExternalStorageDirectory().absolutePath + WACleanerConstants.WA_PATH)
        if (filesDirectory.exists()) {
            var files = filesDirectory.listFiles()
            fileCount = files.size
            for (i in files.indices) {
                if (isToIncludeFolder(files[i])) {
                    totalSize += getDirSize(files[i])
                }
            }
        }
        return WAClenerUtility.getFileSize(totalSize)
    }

    private fun isToIncludeFolder(file: File) =
            (file.absolutePath.endsWith(WACleanerConstants.WA_DATABASES) || file.absolutePath.endsWith(WACleanerConstants.WA_MEDIA))

    private fun getDirSize(file: File?): Long {
        var totalSize = 0L
        var files = file?.listFiles()
        for (i in files?.indices!!) {
            if (files[i].isDirectory) {
                totalSize += getDirSize(files[i])
            } else {
                totalSize += files[i].length()
            }
        }
        return totalSize
    }

    fun updateWATotalSize(activity: AppCompatActivity) {
        fileCount = 0
        var tvCalculating = activity.findViewById<TextView>(R.id.tvCalculating)
        var tvTotalSize = activity.findViewById<TextView>(R.id.tvTotalSize)
        var tvTotalFiles = activity.findViewById<TextView>(R.id.tvTotalFiles)
        CoroutineScope(IO).launch {
            val totalSize = getWATotalSize()
            withContext(Main) {
                tvCalculating.visibility = View.INVISIBLE
                tvTotalSize.visibility = View.VISIBLE
                tvTotalFiles.visibility = View.VISIBLE
                tvTotalSize.text = totalSize
                tvTotalFiles.text = "$fileCount Files Found"
            }
        }
    }
}