package com.watools.wacleaner.module.model

data class WADirectoryItem(
        val dirName: String,
        val dirPath: String,
        val totalFiles: Int,
        val dirSize: String,
        val bgColorCode: Int,
        val icon: Int,
        var isCheckBoxChecked: Boolean)