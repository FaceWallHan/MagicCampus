package com.hhs.campus.utils

import android.net.Uri
import java.io.File

interface OnAddPictureListener {
    fun choosePicture(data: Uri)
    fun takePicture(imageUri:Uri,file:File)
}

interface OnSelectImageItemListener {
    fun onItemClicked(position:Int,status:Boolean)
}
interface OnRemoveCommentListener{
    fun onRemoveListen(commentId:Int,studentId:Int)
    //评论id，学生id
}