package com.hhs.campus.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.hhs.campus.R
import com.hhs.campus.utils.OnAddPictureListener
import kotlinx.android.synthetic.main.image_dialog_layout.*
import java.io.File

class SelectImageDialog : DialogFragment() ,View.OnClickListener{
    private val takePhoto=1
    private lateinit var imageUri: Uri
    private lateinit var outputImage: File
    private val fromAlbum=2
    lateinit var addPictureListener:OnAddPictureListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME,R.style.dialog_custom)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations=R.style.dialog_style
        photo_choose.setOnClickListener(this)
        photograph.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.let {
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.decorView.setPadding(0,0,0,0)
            val attributes = it.attributes
            attributes.gravity=Gravity.BOTTOM
            attributes.width=WindowManager.LayoutParams.MATCH_PARENT
            attributes.height=WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes=attributes
        }
        return inflater.inflate(R.layout.image_dialog_layout,container,false)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.photo_choose->choosePhoto()
            R.id.photograph->takePhoto()
        }
    }
    private fun  choosePhoto(){
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 1) }
        }
        //打开文件选择器
        val intent=Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        //指定只显示图片
        intent.type="image/*"
        startActivityForResult(intent,fromAlbum)
    }
    private fun  takePhoto(){
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 1) }
        }
        //创建file对象，用于存储拍照后的照片
        outputImage=File(activity?.externalCacheDir,"output_image.jpg")
        if (outputImage.exists()){
            outputImage.exists()
        }
        outputImage.createNewFile()
        imageUri=if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(requireActivity(),resources.getString(R.string.authorities),outputImage)
        }else{
            Uri.fromFile(outputImage)
        }
        //启动相机程序
        val intent= Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(intent,takePhoto)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            takePhoto->{
                if (resultCode== Activity.RESULT_OK){
                    //将拍摄的图片显示出来
                    addPictureListener.takePicture(imageUri,outputImage)
                }
            }
            fromAlbum->{
                if (resultCode== Activity.RESULT_OK&&data!=null){
                    data.data ?.let {uri->
//                        //将选择的图片显示
                        addPictureListener.choosePicture(uri)
                    }
                }
            }
        }
    }


}