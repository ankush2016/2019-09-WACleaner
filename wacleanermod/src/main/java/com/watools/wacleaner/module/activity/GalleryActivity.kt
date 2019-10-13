package com.watools.wacleaner.module.activity

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.GalleryAdapter
import com.watools.wacleaner.module.fragments.GalleryFragment
import com.watools.wacleaner.module.presenter.GalleryPresenter
import com.watools.wacleaner.module.utility.WACleanerConstants


class GalleryActivity : AppCompatActivity() {

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var galleryPresenter: GalleryPresenter

    private lateinit var folderType :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initViews()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbarGalleryActivity)
        viewPager = findViewById(R.id.viewPagerGallery)
        tabLayout = findViewById(R.id.tabLayoutGallery)

        galleryPresenter = GalleryPresenter(this)
        folderType = intent.getStringExtra(WACleanerConstants.INTENT_KEY_DIR_TYPE)

        setupToolbar()
        setupViewPager()
        galleryPresenter.changeTabsFont(tabLayout)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar.changeToolbarFont()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun Toolbar.changeToolbarFont() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface =
                    Typeface.create(resources.getString(R.string.app_font), Typeface.NORMAL)
                break
            }
        }
    }

    private fun setupViewPager() {
        galleryAdapter = GalleryAdapter(supportFragmentManager)
        setupAdapterWithFolderType()
        viewPager.adapter = galleryAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupAdapterWithFolderType() {
        when {
            folderType.contentEquals(WACleanerConstants.DIR_VIDEOS) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.VIDEOS_RECEIVED_PATH), WACleanerConstants.TAB_RECEIVED)
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.VIDEOS_SENT_PATH), WACleanerConstants.TAB_SENT)
            }
            folderType.contentEquals(WACleanerConstants.DIR_IMAGES) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.IMAGES_RECEIVED_PATH), WACleanerConstants.TAB_RECEIVED)
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.IMAGES_SENT_PATH), WACleanerConstants.TAB_SENT)
            }
            folderType.contentEquals(WACleanerConstants.DIR_STICKERS) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.STICKERS_DIR_PATH), "")
                tabLayout.visibility = View.GONE
            }
            folderType.contentEquals(WACleanerConstants.DIR_GIFS) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.GIFS_RECEIVED_PATH), WACleanerConstants.TAB_RECEIVED)
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.GIFS_SENT_PATH), WACleanerConstants.TAB_SENT)
            }
            folderType.contentEquals(WACleanerConstants.DIR_AUDIOS) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.AUDIOS_RECEIVED_PATH), WACleanerConstants.TAB_RECEIVED)
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.AUDIOS_SENT_PATH), WACleanerConstants.TAB_SENT)
            }
            folderType.contentEquals(WACleanerConstants.DIR_WALLPAPERS) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.WALLPAPERS_DIR_PATH), "")
                tabLayout.visibility = View.GONE
            }
            folderType.contentEquals(WACleanerConstants.DIR_PROFILE_PHOTOS) -> {
                galleryAdapter.addFragment(GalleryFragment(WACleanerConstants.PROFILE_PHOTOS_DIR_PATH), "")
                tabLayout.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
