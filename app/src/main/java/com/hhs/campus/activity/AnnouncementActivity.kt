package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.hhs.campus.R
import com.hhs.campus.adapter.AnnouncementAdapter
import com.hhs.campus.bean.Announcement
import kotlinx.android.synthetic.main.activity_announcement.*

class AnnouncementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)
        setSupportActionBar(show_announcement)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        val serializable = intent.getSerializableExtra(AnnouncementAdapter.ANNOUNCEMENT_ITEM) as Announcement
        announcement_content.text=serializable.content
        announcement_author.text=serializable.author
        announcement_time.text=serializable.time
        show_announcement.title=serializable.title
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}