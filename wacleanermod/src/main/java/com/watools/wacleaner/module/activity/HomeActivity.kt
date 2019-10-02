package com.watools.wacleaner.module.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.RVWADirectoriesAdapter
import com.watools.wacleaner.module.model.WADirectoryItem
import com.watools.wacleaner.module.presenter.HomePresenter

class HomeActivity : AppCompatActivity() {

    private lateinit var homePresenter: HomePresenter
    private lateinit var tvTotalSize: TextView
    private lateinit var tvCalculating: TextView
    private lateinit var tvTotalFiles: TextView
    private lateinit var rvWADirectories: RecyclerView
    private var waDirectoryDetailList = ArrayList<WADirectoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
    }

    private fun initViews() {
        tvTotalSize = findViewById(R.id.tvTotalSize)
        tvCalculating = findViewById(R.id.tvCalculating)
        tvTotalFiles = findViewById(R.id.tvTotalFiles)
        rvWADirectories = findViewById(R.id.rvWADirectories)

        homePresenter = HomePresenter(this)

        tvCalculating.visibility = View.VISIBLE
        tvTotalSize.visibility = View.INVISIBLE
        tvTotalFiles.visibility = View.INVISIBLE
        homePresenter.updateWATotalSize(this)
        rvWADirectories.layoutManager = LinearLayoutManager(this)
        rvWADirectories.adapter = RVWADirectoriesAdapter(waDirectoryDetailList)

        homePresenter.prepareWADirectoryDetails(rvWADirectories)
    }

    override fun onResume() {
        super.onResume()
        homePresenter.updateWATotalSize(this)
    }
}
