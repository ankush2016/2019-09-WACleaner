package com.watools.wacleaner.module.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R

class DocumentFragment() : Fragment() {

    private lateinit var rvDocument: RecyclerView
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
        rvDocument = view?.findViewById(R.id.rvGallery)!!
        tvNoData = view?.findViewById(R.id.tvNoData)


    }
}