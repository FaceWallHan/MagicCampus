package com.hhs.campus.utils

import android.net.Uri
import java.io.File

interface OnAddPictureListener {
    fun choosePicture(data: Uri)
    fun takePicture(imageUri:Uri,file:File)
}