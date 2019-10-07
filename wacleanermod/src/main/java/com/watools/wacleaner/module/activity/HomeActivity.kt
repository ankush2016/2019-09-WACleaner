package com.watools.wacleaner.module.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.RVWADirectoriesAdapter
import com.watools.wacleaner.module.model.WADirectoryItem
import com.watools.wacleaner.module.presenter.HomePresenter

class HomeActivity : AppCompatActivity(), RVWADirectoriesAdapter.CBCheckChangeListener {

    private lateinit var homePresenter: HomePresenter
    private lateinit var tvTotalSize: TextView
    private lateinit var tvCalculating: TextView
    private lateinit var tvTotalFiles: TextView
    private lateinit var rvWADirectories: RecyclerView
    private var waDirectoryDetailList = ArrayList<WADirectoryItem>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var linearLayoutBottomSheet: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomSheetClearData()
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
        rvWADirectories.adapter = RVWADirectoriesAdapter(waDirectoryDetailList, HomeActivity@this)

        homePresenter.prepareWADirectoryDetails(rvWADirectories, this)
    }

    private fun setupBottomSheetClearData() {
        linearLayoutBottomSheet = findViewById(R.id.clearDataBottomSheetLayout)
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomSheet)
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(view: View, p1: Float) {}
        })
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onResume() {
        super.onResume()
        homePresenter.updateWATotalSize(this)
    }

    override fun onCheckChanged(isChecked: Boolean) {
        if (isChecked) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}
