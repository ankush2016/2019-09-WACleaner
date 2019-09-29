package com.watools.wacleaner.module.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class GalleryAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentList = arrayListOf<Fragment>()
    private val fragmentTitleList = arrayListOf<String>()

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}