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

    var totalWAFiles = 0
    private lateinit var tvTotalFiles: TextView

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

    fun updateWATotalSize(activity: AppCompatActivity) {
        var tvCalculating = activity.findViewById<TextView>(R.id.tvCalculating)
        var tvTotalSize = activity.findViewById<TextView>(R.id.tvTotalSize)
        tvTotalFiles = activity.findViewById<TextView>(R.id.tvTotalFiles)
        CoroutineScope(IO).launch {
            val fileCount = WAClenerUtility.getTotalFiles(WACleanerConstants.WA_PATH)
            val totalSize = getWATotalSize()
            withContext(Main) {
                tvCalculating.visibility = View.INVISIBLE
                tvTotalSize.visibility = View.VISIBLE
                tvTotalFiles.visibility = View.VISIBLE
                tvTotalSize.text = totalSize
            }
        }
    }

    fun prepareWADirectoryDetails(rvWADirectories: RecyclerView) {
        CoroutineScope(IO).launch {
            var waDirectoryDetailList = ArrayList<WADirectoryItem>()

            addItemsToWaDirList(WACleanerConstants.VIDEOS_DIR_PATH, WACleanerConstants.DIR_VIDEOS, R.color.color_ca1d13, R.drawable.ic_wc_videos, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.IMAGES_DIR_PATH, WACleanerConstants.DIR_IMAGES, R.color.color_9e25b4, R.drawable.ic_wc_images, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.DATABASES_DIR_PATH, WACleanerConstants.DIR_DATABASES, R.color.color_049587, R.drawable.ic_wc_database, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.DOCUMENTS_DIR_PATH, WACleanerConstants.DIR_DOCUMENTS, R.color.color_673aba, R.drawable.ic_wc_documents, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.STICKERS_DIR_PATH, WACleanerConstants.DIR_STICKERS, R.color.color_1297e3, R.drawable.ic_wc_sticker, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.GIFS_DIR_PATH, WACleanerConstants.DIR_GIFS, R.color.color_ea1b62, R.drawable.ic_wc_gifs, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.VOICE_NOTES_DIR_PATH, WACleanerConstants.DIR_VOICE_NOTES, R.color.color_04bbd1, R.drawable.ic_wc_voice_notes, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.AUDIOS_DIR_PATH, WACleanerConstants.DIR_AUDIOS, R.color.color_ff9603, R.drawable.ic_wc_audios, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.WALLPAPERS_DIR_PATH, WACleanerConstants.DIR_WALLPAPERS, R.color.color_607c8a, R.drawable.ic_wc_wallpaper, waDirectoryDetailList)
            addItemsToWaDirList(WACleanerConstants.PROFILE_PHOTOS_DIR_PATH, WACleanerConstants.DIR_PROFILE_PHOTOS, R.color.color_2395f2, R.drawable.ic_wc_profile_photos, waDirectoryDetailList)

            withContext(Main) {
                rvWADirectories.adapter = RVWADirectoriesAdapter(waDirectoryDetailList)
                (rvWADirectories.adapter as RVWADirectoriesAdapter).notifyDataSetChanged()
                tvTotalFiles.text = "$totalWAFiles Files Found"
            }
        }
    }

    private suspend fun addItemsToWaDirList(dirPath: String, dirName: String, bgColor: Int, bgImage: Int, waDirectoryDetailList: ArrayList<WADirectoryItem>) {
        var totalSizeFileCountPair: Pair<String, Int> = WAClenerUtility.getDirSizeAndTotalFiles(dirPath)
        var totalFiles = WAClenerUtility.getTotalFiles(dirPath)
        totalWAFiles += totalFiles
        //var waDirectoryItem = WADirectoryItem(dirName, dirPath,  totalSizeFileCountPair.second, totalSizeFileCountPair.first, bgColor, bgImage)
        var waDirectoryItem = WADirectoryItem(dirName, dirPath, totalFiles, totalSizeFileCountPair.first, bgColor, bgImage)
        waDirectoryDetailList.add(waDirectoryItem)
    }
}