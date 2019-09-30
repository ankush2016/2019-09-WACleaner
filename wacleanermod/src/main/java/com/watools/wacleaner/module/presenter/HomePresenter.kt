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
}