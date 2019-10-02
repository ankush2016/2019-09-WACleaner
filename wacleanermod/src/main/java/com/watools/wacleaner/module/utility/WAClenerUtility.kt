package com.watools.wacleaner.module.utility

import android.os.Environment
import kotlinx.coroutines.delay
import java.io.File
import kotlin.math.ln


object WAClenerUtility {

    fun getFileSize(bytes: Long): String {
        val unit = 1024
        if (bytes < unit) {
            return "$bytes +  B"
        }
        val exp = (Math.log(bytes.toDouble()) / ln(unit.toDouble())).toInt()
        val pre = ("kMGTPE")[exp - 1]
        return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
    }

    suspend fun getDirSizeAndTotalFiles(dirPath: String): Pair<String, Int> {
        //delay(5000)
        var totalSize = 0L
        var fileCount = 0
        var filesDirectory = File(Environment.getExternalStorageDirectory().absolutePath + dirPath)
        if (filesDirectory.exists()) {
            var files = filesDirectory.listFiles()
            fileCount = files.size
            for (i in files.indices) {
                if (files[i].isDirectory) {
                    totalSize += getDirSize(files[i])
                } else {
                    totalSize += files[i].length()
                }
            }
        }
        return Pair(getFileSize(totalSize), fileCount)
    }

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