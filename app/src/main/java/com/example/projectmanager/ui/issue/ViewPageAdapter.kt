package com.example.projectmanager.ui.issue

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPageAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {

    val fragments: MutableList<Fragment> = ArrayList()
    val titles: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CreatedFragment.newInstance()
            1 -> StartedFragment.newInstance()
            2 -> FinishedFragment.newInstance()
            else -> CreatedFragment()
        }
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}