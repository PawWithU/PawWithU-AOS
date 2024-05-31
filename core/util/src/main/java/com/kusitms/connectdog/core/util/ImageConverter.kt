package com.kusitms.connectdog.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageConverter {
    fun uriToFile(context: Context, uri: Uri, quality: Int): File? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val tempFile = File.createTempFile("compressed_", ".jpg", context.cacheDir)
        val outputStream = FileOutputStream(tempFile)

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()

        return tempFile
    }
}