package com.hhs.campus.fragment

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.hhs.campus.R
import com.hhs.campus.activity.LoginActivity
import com.hhs.campus.adapter.OperateAdapter
import com.hhs.campus.bean.ImageHeader
import com.hhs.campus.bean.Student
import com.hhs.campus.databinding.MineInfoLayoutBinding
import com.hhs.campus.dialog.SelectImageDialog
import com.hhs.campus.utils.FileUtil
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.OnAddPictureListener
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.function.Consumer

@AndroidEntryPoint
class MineInfoFragment:BaseViewFragment<MineInfoLayoutBinding>(), Consumer<Int>, OnAddPictureListener {
    val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val imageDialog  by lazy {
        val dialog= SelectImageDialog()
        dialog.addPictureListener=this
        dialog}
    private lateinit var fileName:String
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.refreshSelfInquire()
        viewModel.inquireLiveData.observe(viewLifecycleOwner, Observer {
            binding.student=it
            binding.headIcon.load(it.avatar)
        })
        val  list= mutableListOf(resources.getString(R.string.exit))
        val adapter=OperateAdapter(list)
        adapter.itemListener=this
        val layoutManager=LinearLayoutManager(activity)
        binding.infoList.adapter=adapter
        binding.infoList.layoutManager=layoutManager
        binding.headIcon.setOnClickListener {imageDialog.show(childFragmentManager,"") }
        observerSomeThing()

    }
    private fun observerSomeThing(){
        viewModel.saveLiveData.observe(viewLifecycleOwner, Observer { result->
            if (result){
                activity?.let {
                    val intent= Intent(it,LoginActivity::class.java)
                    startActivity(intent)
                    it.finish()
                }
            }
        })
        viewModel.imageLiveData.observe(viewLifecycleOwner, Observer { result->
            if (result.isSuccess()){
                result.data.let { data ->
                    fileName=data.fileName
                    //上传成功获取imageUrl
                    binding.student?.id?.let { viewModel.updateHead(ImageHeader(it,fileName))}
                }
                "上传成功".showToast()
            }else{
                "上传失败".showToast()
            }
        })
        viewModel.headLiveData.observe(viewLifecycleOwner, Observer { result->
            if (result.isSuccess()){
                binding.student?.let {
                    binding.headIcon.load(fileName)
                    it.avatar=fileName
                    viewModel.saveStudent(it)
                }
                "更改成功".showToast()
            }else{
                "更改失败".showToast()
            }
        })
    }
    override fun getSubLayoutId()=R.layout.mine_info_layout
    override fun accept(t: Int) {
        when(t){
            0->{
                ImageUtil.showAlertDialog(requireContext(),"确定退出吗？",
                DialogInterface.OnClickListener { _, _ ->
                    viewModel.saveStudent(Student())
                    viewModel.saveLiveData.observe(this, Observer {
                        activity?.finish()
                        startActivity(Intent(requireContext(),LoginActivity::class.java))
                    })
                },null)
            }
        }
    }

    override fun choosePicture(data: Uri) {
        activity?.let {
            val bitmap= ImageUtil.getBitmapFromUri(data, it )
            binding.headIcon.load(bitmap)
            imageDialog.dismiss()
            val fileName = FileUtil.getPath(it,data)
            ImageUtil.uploadLocalImage(File(fileName),it,lifecycleScope){ part->
                viewModel.uploadFile(part)
            }
        }
    }

    override fun takePicture(imageUri: Uri, file: File) {
        activity?.let {
            val bitmap= BitmapFactory.decodeStream(it.contentResolver.openInputStream(imageUri))
            binding.headIcon.setImageBitmap(ImageUtil.rotateIfRequired(bitmap,file))
            imageDialog.dismiss()
            ImageUtil.uploadLocalImage(file,it,lifecycleScope){ part->
                viewModel.uploadFile(part)
            }
        }
    }
}