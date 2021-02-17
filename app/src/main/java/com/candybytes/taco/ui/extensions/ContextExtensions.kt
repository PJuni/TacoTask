package com.candybytes.taco.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun Context.getBitmapBy(uri: Uri): Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
    ImageDecoder.decodeBitmap(
        ImageDecoder.createSource(
            contentResolver,
            uri
        )
    )
else MediaStore.Images.Media.getBitmap(contentResolver, uri)