package com.watools.wacleaner.module.presenter

import android.content.Context
import android.os.Environment
import com.watools.wacleaner.module.utility.WACleanerConstants
import com.watools.wacleaner.module.utility.WAClenerUtility
import java.io.File

class HomePresenter(val context: Context) {

    fun getWATotalSize(): String {
        var totalSize = 0L
        var filesDirectory = File(Environment.getExternalStorageDirectory().absolutePath + WACleanerConstants.WA_PATH)
        if (filesDirectory.exists()) {
            var files = filesDirectory.listFiles()
            for (i in files.indices) {
                totalSize += files[i].length()
            }
        }
        return WAClenerUtility.getFileSize(totalSize)
    }
}