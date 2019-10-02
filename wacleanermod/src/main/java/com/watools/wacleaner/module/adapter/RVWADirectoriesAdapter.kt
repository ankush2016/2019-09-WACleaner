package com.watools.wacleaner.module.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.model.WADirectoryItem
import java.lang.StringBuilder

class RVWADirectoriesAdapter(val waDirectoryDetailList: ArrayList<WADirectoryItem>) :
    RecyclerView.Adapter<RVWADirectoriesAdapter.DirectoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryItemViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cm_wa_directory_item, parent, false)
        return DirectoryItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return waDirectoryDetailList.size
    }

    override fun onBindViewHolder(holder: DirectoryItemViewHolder, position: Int) {
        holder.tvDirectoryName.text = waDirectoryDetailList[position]?.dirName
        var text =
            StringBuilder().append(waDirectoryDetailList[position]?.totalFiles).append(" Files ")
                .append(waDirectoryDetailList[position]?.dirSize)
        holder.tvDirDetails.text = text
    }

    class DirectoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDirectoryName = itemView.findViewById<TextView>(R.id.tvDirectoryName)
        val tvDirDetails = itemView.findViewById<TextView>(R.id.tvDirDetails)
    }
}