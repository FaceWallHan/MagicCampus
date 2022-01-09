package com.hhs.campus.dialog

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.hhs.campus.R
import com.hhs.campus.utils.ChoosePhotoContracts
import com.hhs.campus.utils.OnAddPictureListener
import com.hhs.campus.utils.OtherUtils
import kotlinx.android.synthetic.main.image_dialog_layout.*
import java.io.File

class SelectImageDialog : DialogFragment() ,View.OnClickListener{
    private lateinit var imageUri: Uri
    private lateinit var outputImage: File
    lateinit var addPictureListener:OnAddPictureListener
    private val requestTakePermission=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        OtherUtils.checkPermissionMap(it){operatePhoto()}
    }
    private val requestTakeData=registerForActivityResult(ActivityResultContracts.TakePicture()){
        if (it) addPictureListener.takePicture(imageUri,outputImage)
    }
    private val requestChooseData=registerForActivityResult(ChoosePhotoContracts()){ addPictureListener.choosePicture(it) }
    private val requestChoosePermission=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        OtherUtils.checkPermissionMap(it){requestChooseData.launch(null)}
    }
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
            R.id.photo_choose->OtherUtils.checkObtainPermission(requestChoosePermission){ requestChooseData.launch(null) }
            R.id.photograph->OtherUtils.checkObtainPermission(requestTakePermission){ operatePhoto() }
        }
    }
    private fun operatePhoto(){
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
        requestTakeData.launch(imageUri)
    }
}