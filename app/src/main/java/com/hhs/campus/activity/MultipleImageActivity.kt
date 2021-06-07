package com.hhs.campus.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.adapter.MultipleImageAdapter
import com.hhs.campus.bean.DynamicImage
import com.hhs.campus.utils.OnSelectImageItemListener
import kotlinx.android.synthetic.main.activity_multiple_image.*
import java.io.File
import java.util.stream.Collectors
import kotlin.properties.Delegates

class MultipleImageActivity : AppCompatActivity() ,View.OnClickListener,AdapterView.OnItemSelectedListener,
    OnSelectImageItemListener {
    private val localDir="/storage/emulated/0/"
    private val dirList=ArrayList<String>()
    private val imgList=ArrayList<DynamicImage>()
    private val adapter=MultipleImageAdapter(imgList,this)
    private var imageCount by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_image)
        initSome()
    }
    private fun initSome(){
        imageCount=intent.getIntExtra(AppClient.imageCount,9)
        val layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        multiple_list.layoutManager=layoutManager
        adapter.onSelectImageItemListener=this
        multiple_list.adapter=adapter
        exit_choose.setOnClickListener(this)
        finish_choose.setOnClickListener(this)
        dirList.addAll(getPhotosDirs(contentResolver))
        val spinnerAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,dirList)
        image_dir.adapter=spinnerAdapter
        image_dir.onItemSelectedListener=this
    }
    private fun getPhotosDirs(resolver: ContentResolver): ArrayList<String>{
        val galleryList =  ArrayList<String>()
        val pathSet=HashSet<String>()
        val columns= arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE)
        val orderBy= MediaStore.Images.Media.DATE_MODIFIED
        val imageCursor=resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,null,null,orderBy)
        if (imageCursor!=null&&imageCursor.count>0){
            pathSet.stream().collect(Collectors.toList())
            while (imageCursor.moveToNext()){
                val dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val path=imageCursor.getString(dataColumnIndex)
                val parentFile=File(path).parentFile
                var dir=parentFile.absolutePath
                if (dir.contains(localDir)){
                    dir=dir.replace(localDir,"",false)
                    if (dir.split(File.separator).size<4){
                        pathSet.add(dir)
                    }
                }
            }
        }
        for (s in pathSet) {
            galleryList.add(s)
        }
        galleryList.reverse()
        return galleryList
    }
    private fun refreshImageListByDir(dir:String){
        imgList.clear()
        val directory=File(dir)
        if (directory.exists()){
            val listFiles = directory.listFiles()
            for (file in listFiles) {
                val path = file.absolutePath
                imgList.add(DynamicImage(path))
            }
        }
        adapter.notifyDataSetChanged()
    }
    private fun getSelectCount():Int{
        var count=0
        for (image in imgList) {
            if (image.isCheck){
                count++
            }
        }
        return count
    }
    private fun getSelectList():ArrayList<DynamicImage>{
        val list=ArrayList<DynamicImage>()
        for (image in imgList) {
            if (image.isCheck){
                list.add(image)
            }
        }
        return list
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.exit_choose->finish()
            R.id.finish_choose->{
                val intent=Intent()
                intent.putExtra(AppClient.imageList,getSelectList())
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        refreshImageListByDir(localDir+dirList[p2])
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClicked(position: Int, status: Boolean) {
        finish_choose.isEnabled = getSelectCount() in 1..imageCount
        finish_choose.text= "完成${getSelectCount()}/${imageCount}"
    }
}