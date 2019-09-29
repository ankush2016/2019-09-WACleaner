package com.watools.wacleaner.module.presenter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.watools.wacleaner.module.R

class GalleryPresenter(val context: Context) {

    fun changeTabsFont(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.typeface =
                        Typeface.create(context.resources.getString(R.string.app_font), Typeface.NORMAL)
                }
            }
        }
    }
}