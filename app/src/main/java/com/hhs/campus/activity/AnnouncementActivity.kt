package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hhs.campus.R
import com.hhs.campus.adapter.AnnouncementAdapter
import com.hhs.campus.bean.Announcement
import com.hhs.campus.databinding.ActivityAnnouncementBinding

class AnnouncementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<ActivityAnnouncementBinding>(this,R.layout.activity_announcement)
        setSupportActionBar(binding.showAnnouncement)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        val serializable = intent.getSerializableExtra(AnnouncementAdapter.ANNOUNCEMENT_ITEM) as Announcement
        binding.announcement=serializable
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}