package com.watools.wacleaner.module.presenter

import android.content.Context
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.RVWADirectoriesAdapter
import com.watools.wacleaner.module.model.WADirectoryItem
import com.watools.wacleaner.module.utility.WACleanerConstants
import com.watools.wacleaner.module.utility.WAClenerUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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

    fun prepareWADirectoryDetails(rvWADirectories: RecyclerView) {
        CoroutineScope(IO).launch {
            var waDirectoryDetailList = ArrayList<WADirectoryItem>()

            addItemsToWaDirList(WACleanerConstants.VIDEOS_DIR_PATH, WACleanerConstants.DIR_VIDEOS, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.IMAGES_DIR_PATH, WACleanerConstants.DIR_IMAGES, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.DATABASES_DIR_PATH, WACleanerConstants.DIR_DATABASES, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.DOCUMENTS_DIR_PATH, WACleanerConstants.DIR_DOCUMENTS, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.STICKERS_DIR_PATH, WACleanerConstants.DIR_STICKERS, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.GIFS_DIR_PATH, WACleanerConstants.DIR_GIFS, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.VOICE_NOTES_DIR_PATH, WACleanerConstants.DIR_VOICE_NOTES, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.AUDIOS_DIR_PATH, WACleanerConstants.DIR_AUDIOS, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.WALLPAPERS_DIR_PATH, WACleanerConstants.DIR_WALLPAPERS, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.PROFILE_PHOTOS_DIR_PATH, WACleanerConstants.DIR_PROFILE_PHOTOS, waDirectoryDetailList)

            withContext(Main) {
                rvWADirectories.adapter = RVWADirectoriesAdapter(waDirectoryDetailList)
                (rvWADirectories.adapter as RVWADirectoriesAdapter).notifyDataSetChanged()
            }
        }
    }

    private suspend fun addItemsToWaDirList(dirPath: String, dirName: String, waDirectoryDetailList: ArrayList<WADirectoryItem>) {
        var totalSizeFileCountPair: Pair<String, Int> = WAClenerUtility.getDirSizeAndTotalFiles(dirPath)
        var waDirectoryItem = WADirectoryItem(dirName, dirPath, totalSizeFileCountPair.second, totalSizeFileCountPair.first)
        waDirectoryDetailList.add(waDirectoryItem)
    }
}