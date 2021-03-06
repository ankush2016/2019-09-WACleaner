package com.watools.wacleaner.module.utility

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.model.GalleryItem
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
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

    fun deleteFilesFromDir(dirPath: String) {
        var directory = File(Environment.getExternalStorageDirectory().absolutePath + dirPath)
        FileUtils.deleteDirectory(directory)
    }

    fun showAlertDialog(activity: AppCompatActivity, message: String, listener: AlertDialogClickListener) {
        var dialog = Dialog(activity)
        dialog.setContentView(R.layout.delete_alert_dialog_layout)
        dialog.findViewById<Button>(R.id.btnDialogNo).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<Button>(R.id.btnDialogOk).setOnClickListener {
            dialog.dismiss()
            listener.onPositiveButtonClick(activity)
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    fun getDataFromStorage(dirPath: String): ArrayList<GalleryItem> {
        var dataList = ArrayList<GalleryItem>()
        var filesDirectory = File(Environment.getExternalStorageDirectory().absolutePath + dirPath)

        if (filesDirectory.exists()) {
            var files = filesDirectory.listFiles()
            Arrays.sort(files, object : Comparator<File> {
                override fun compare(f1: File?, f2: File?): Int {
                    val s1 = f1?.lastModified()
                    val s2 = f2?.lastModified()
                    if (s1 != null && s2 != null) {
                        if (s1 == s2) {
                            return 0
                        } else if (s1 < s2) {
                            return 1
                        } else {
                            return -1
                        }
                    }
                    return -1
                }
            })
            for (i in files.indices) {
                var item = files[i]
                dataList.add(GalleryItem(item.name, Uri.fromFile(item), getFileSize(item.length())))
            }
        }
        return dataList
    }

    interface AlertDialogClickListener {
        fun onPositiveButtonClick(activity: AppCompatActivity)
    }
}