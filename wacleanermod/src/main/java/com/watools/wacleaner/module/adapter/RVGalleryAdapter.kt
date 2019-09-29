package com.watools.wacleaner.module.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.watools.wacleaner.module.model.GalleryItem
import com.watools.wacleaner.module.R

class RVGalleryAdapter(val dataList: ArrayList<GalleryItem>) : RecyclerView.Adapter<RVGalleryAdapter.GalleryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.cm_gallery_item, parent, false)
        return GalleryItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.updateView(position, dataList)
    }

    class GalleryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val margin = 5

        val clItemRoot = itemView.findViewById<ConstraintLayout>(R.id.clItemRoot)
        val ivThumbnail = itemView.findViewById<ImageView>(R.id.ivThumbnail)
        val tvSize = itemView.findViewById<TextView>(R.id.tvSize)

        fun updateView(position: Int, dataList: ArrayList<GalleryItem>) {
            updateMargins(position)

            Glide.with(itemView.context).load(dataList[position].uri).into(ivThumbnail)
            tvSize.text = dataList[position].fileSize
            itemView.setOnLongClickListener(object : View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {

                    return true
                }
            })
        }

        private fun updateMargins(position: Int) {
            var params = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, 0)
            if (position % 3 == 0 || position % 3 == 1) {
                params.setMargins(0, 0, margin, 0)
            }
            params.bottomMargin = margin
            clItemRoot.layoutParams = params
        }
    }
}