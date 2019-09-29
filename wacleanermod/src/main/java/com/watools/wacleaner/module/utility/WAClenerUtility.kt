package com.watools.wacleaner.module.utility

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
}