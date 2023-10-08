package com.example.billcalculator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream


fun captureBillAsScreenshot(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun shareBillAsScreenshot(context: Context, bitmap: Bitmap) {
    val file = File(context.externalCacheDir, "screenshot.png")
    try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val contentUri =
        FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", file)

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, contentUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_bill)
        )
    )
}

fun shareBillAsText(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_bill)
        )
    )
}
