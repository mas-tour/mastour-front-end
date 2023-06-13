package com.mastour.mastour.util

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDate
import java.time.ZoneOffset

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

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    return email.matches(emailRegex)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAgeFromTimestamp(timestamp: Long): Long {
    val currentDate = LocalDate.now()
    val birthDate = LocalDate.ofEpochDay(timestamp / (24 * 60 * 60)).atStartOfDay(ZoneOffset.UTC).toLocalDate()
    val age = (currentDate.year - birthDate.year).toLong()
    return if (birthDate.plusYears(age) > currentDate) age - 1 else age
}


private fun createTempFile(context: Context): File {
    val fileName = "temp_file"
    val directory = context.cacheDir
    return File(directory, fileName)
}

fun booleanToInt(b: Boolean): Int {
    return if (b) 1 else 0
}