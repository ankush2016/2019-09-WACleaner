package com.watools.wacleaner.module.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R

class RVWADirectoriesAdapter :
    RecyclerView.Adapter<RVWADirectoriesAdapter.DirectoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryItemViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cm_wa_directory_item, parent, false)
        return DirectoryItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: DirectoryItemViewHolder, position: Int) {
    }

    class DirectoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}