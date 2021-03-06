package com.watools.wacleaner.module.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.RVWADirectoriesAdapter
import com.watools.wacleaner.module.model.WADirectoryItem
import com.watools.wacleaner.module.presenter.HomePresenter
import com.watools.wacleaner.module.utility.WACleanerConstants
import com.watools.wacleaner.module.utility.WAClenerUtility

class HomeActivity : AppCompatActivity(), RVWADirectoriesAdapter.CBCheckChangeListener, View.OnClickListener {

    private lateinit var homePresenter: HomePresenter
    private lateinit var tvTotalSize: TextView
    private lateinit var tvCalculating: TextView
    private lateinit var tvTotalFiles: TextView
    private lateinit var llClearData: LinearLayout
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
        rvWADirectories.adapter = RVWADirectoriesAdapter(waDirectoryDetailList, this)

        /*
        getting called from onResume()
        homePresenter.prepareWADirectoryDetails(rvWADirectories, this, waDirectoryDetailList)
         */
    }

    private fun setupBottomSheetClearData() {
        linearLayoutBottomSheet = findViewById(R.id.clearDataBottomSheetLayout)
        llClearData = findViewById(R.id.llClearData)
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
        llClearData.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        homePresenter.updateWATotalSize(this)
        homePresenter.prepareWADirectoryDetails(rvWADirectories, this, waDirectoryDetailList)
    }

    override fun onCheckChanged(isChecked: Boolean) {
        var isAnyItemChecked = false
        for (i in waDirectoryDetailList.indices) {
            if (waDirectoryDetailList[i].isCheckBoxChecked) {
                isAnyItemChecked = true
            }
        }
        if (isAnyItemChecked) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onClick(v: View?) {
        if (v == llClearData) {
            WAClenerUtility.showAlertDialog(this, WACleanerConstants.DELETE_MSG, object : WAClenerUtility.AlertDialogClickListener {
                override fun onPositiveButtonClick(activity: AppCompatActivity) {
                    homePresenter.clearDirectories(waDirectoryDetailList)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    homePresenter.updateWATotalSize(activity)
                    homePresenter.prepareWADirectoryDetails(rvWADirectories, activity as HomeActivity, waDirectoryDetailList)
                }
            })
        }
    }
}
