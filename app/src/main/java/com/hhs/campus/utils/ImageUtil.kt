package com.hhs.campus.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.hhs.campus.R
import com.hhs.campus.bean.ImageShow
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File

object ImageUtil {
    fun getBitmapFromUri(uri: Uri, activity: Activity) =
        activity.contentResolver.openFileDescriptor(uri, "r")?.use {
            //use标准函数
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height,
            matrix, true
        )
        bitmap.recycle() // 将不再需要的Bitmap对象回收
        return rotatedBitmap
    }

    fun rotateIfRequired(bitmap: Bitmap, outputImage: File): Bitmap {
        val exif = ExifInterface(outputImage.path)
        return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    fun uploadMultipleImage(list: List<ImageShow>, context: Context, scope: CoroutineScope, block: (part: List<MultipartBody.Part>) -> Unit) {
        OtherUtils.checkObtainPermission(null){
            val param = ArrayList<MultipartBody.Part>()
            scope.launch {
                for (show in list) {
                    if (!show.isFirst) {
                        val file = File(show.path)
                        param.add(compressFile(file, context))
                    }
                }
                block(param)
            }
        }
    }
    private  suspend fun compressFile(file: File,context: Context):MultipartBody.Part{
        var lastFile = Compressor.compress(context, file, Dispatchers.IO)
        while (lastFile.length() > 500 * 1000) {
            //将图片压缩至500KB以内
            lastFile = Compressor.compress(context, lastFile, Dispatchers.IO)
        }
        val body = MultipartBody.create(MediaType.parse("image/*"), lastFile)
        return MultipartBody.Part.createFormData("fileName", lastFile.name, body)
    }
    fun uploadLocalImage(file: File, context: Context, scope: CoroutineScope, block: (part: MultipartBody.Part) -> Unit) {
        OtherUtils.checkObtainPermission(null){
            scope.launch {
                val part = compressFile(file, context)
                block(part)
            }
        }
    }

    fun showAlertDialog(
        context: Context, message: String, positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ): AlertDialog = AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton("确定", positiveListener)
        .setNegativeButton("取消", negativeListener)
        .show()
    fun showNetAlertDialog(context: Context){
        showAlertDialog(context,context.resources.getString(R.string.net_change),
            DialogInterface.OnClickListener { _, _ ->
                context.startActivity(Intent(Settings.ACTION_SETTINGS))
            },null)
    }
}