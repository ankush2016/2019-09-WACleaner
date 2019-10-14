package com.watools.wacleaner.module.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.RVGalleryAdapter
import com.watools.wacleaner.module.utility.CMViewDialog
import com.watools.wacleaner.module.utility.WAClenerUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GalleryFragment() : Fragment() {

    private lateinit var rvGallery: RecyclerView
    private lateinit var tvNoData: TextView
    private lateinit var dirPath: String

    constructor(dirPath: String) : this() {
        this.dirPath = dirPath
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_gallery, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View?) {
        rvGallery = view?.findViewById(R.id.rvGallery)!!
        tvNoData = view?.findViewById(R.id.tvNoData)

        val dialog = CMViewDialog(activity)
        dialog.showDialog()

        CoroutineScope(IO).launch {
            var dataList = WAClenerUtility.getDataFromStorage(dirPath)
            withContext(Main) {
                dialog.hideDialog()
                if (dataList.size == 0) {
                    rvGallery.visibility = View.INVISIBLE
                    tvNoData.visibility = View.VISIBLE
                } else {
                    rvGallery.layoutManager = GridLayoutManager(view.context, 3)
                    rvGallery.adapter = RVGalleryAdapter(dataList)
                }
            }
        }
    }

}
