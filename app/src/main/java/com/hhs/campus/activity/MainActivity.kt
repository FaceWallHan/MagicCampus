package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.hhs.campus.R
import com.hhs.campus.StudentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.slide_layout.*

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu)
        }
        navMenu.setOnNavigationItemSelectedListener { item ->
            toolbar.title = item.title.toString()
            true
        }
        navView.setNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.exit->AlertDialog.Builder(this)
                    .setMessage("")
                        
                                .create()
            }
            drawerLayout.closeDrawers()
            true
        }
        viewModel.refreshSelfInquire()
        viewModel.studentLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                val student = result.getOrNull()
                student?.let {
                    id_tv.text=it.id.toString()
                    name_tv.text=it.name
                    headIcon.load(it.avatar)
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}