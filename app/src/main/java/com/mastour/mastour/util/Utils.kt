package com.mastour.mastour.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.temporal.ChronoUnit

suspend fun uriToFile(uri: Uri, context: Context): File = withContext(Dispatchers.IO) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = createTempFile(context)
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return@withContext file
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAgeFromTimestamp(timestamp: Long): Int {
    Log.d("CalculateStamp", "$timestamp")
    val currentDate = LocalDate.now()
    Log.d("CalculateStamp", "$currentDate")
    val birthDate = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
    Log.d("CalculateStamp", "$birthDate")
    val age = ChronoUnit.YEARS.between(birthDate, currentDate).toInt()
    return age
}


private fun createTempFile(context: Context): File {
    val fileName = "temp_file"
    val directory = context.cacheDir
    return File(directory, fileName)
}