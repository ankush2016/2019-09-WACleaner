package com.watools.wacleaner.module.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.presenter.HomePresenter

class HomeActivity : AppCompatActivity() {

    private lateinit var homePresenter: HomePresenter
    private lateinit var tvTotalSize:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
    }

    private fun initViews() {
        tvTotalSize = findViewById(R.id.tvTotalSize)

        homePresenter = HomePresenter(this)
        tvTotalSize.text = homePresenter.getWATotalSize()
    }
}
