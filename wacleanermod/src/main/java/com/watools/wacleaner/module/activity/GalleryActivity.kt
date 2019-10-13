package com.watools.wacleaner.module.activity

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.GalleryAdapter
import com.watools.wacleaner.module.fragments.GalleryFragment
import com.watools.wacleaner.module.presenter.GalleryPresenter


class GalleryActivity : AppCompatActivity() {

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var galleryPresenter: GalleryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initViews()

        setupToolbar()
        setupViewPager()
        galleryPresenter.changeTabsFont(tabLayout)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbarGalleryActivity)
        viewPager = findViewById(R.id.viewPagerGallery)
        tabLayout = findViewById(R.id.tabLayoutGallery)

        galleryPresenter = GalleryPresenter(this)
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
        galleryAdapter.addFragment(GalleryFragment(), "ONE")
        viewPager.adapter = galleryAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
