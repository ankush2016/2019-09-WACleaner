package com.watools.wacleaner.module.utility

import android.os.Environment
import java.io.File
import kotlin.math.ln


object WAClenerUtility {

    var fileCount = 0

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
        var totalSize = 0L
        fileCount = 0
        var filesDirectory = File(Environment.getExternalStorageDirectory().absolutePath + dirPath)
        if (filesDirectory.exists()) {
            var files = filesDirectory.listFiles()
            for (i in files.indices) {
                if (files[i].isDirectory) {
                    totalSize += getDirSize(files[i])
                } else {
                    fileCount++
                    totalSize += files[i].length()
                }
            }
        }
        return Pair(getFileSize(totalSize), fileCount)
    }

    private fun getDirSize(file: File?): Long {
        var totalSize = 0L
        var files = file?.listFiles()
        if (files != null) {
            for (i in files?.indices!!) {
                if (files[i].isDirectory) {
                    totalSize += getDirSize(files[i])
                } else {
                    fileCount++
                    totalSize += files[i].length()
                }
            }
        }
        return totalSize
    }

    fun getTotalFiles(dirPath: String): Int {
        var fileCountt = 0
        var f = File(Environment.getExternalStorageDirectory().absolutePath + dirPath)
        var files = f.listFiles()
        if (files != null) {
            for (i in files.indices) {
                fileCountt++
                var file = files[i]
                if (file.isDirectory) {
                    getTotalFiles(file.absolutePath)
                }
            }
        }
        return fileCountt
    }
}