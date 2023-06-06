package com.mastour.mastour.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

fun uriToFile(uri: Uri, context: Context): File? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = createTempFile(context)
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return file
}

private fun createTempFile(context: Context): File {
    val fileName = "temp_file"
    val directory = context.cacheDir
    return File(directory, fileName)
}