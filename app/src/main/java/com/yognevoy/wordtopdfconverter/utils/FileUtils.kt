package com.yognevoy.wordtopdfconverter.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

object FileUtils {
    fun getFileSize(context: Context, uri: Uri): Long {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        var size: Long = 0
        cursor?.use {
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            if (it.moveToFirst()) {
                size = it.getLong(sizeIndex)
            }
        }
        return size
    }

}
