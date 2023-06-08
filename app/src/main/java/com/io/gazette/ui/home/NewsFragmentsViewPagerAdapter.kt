package com.io.gazette.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.io.gazette.news.businessNews.BusinessNewsFragment
import com.io.gazette.news.healthNews.HealthNewsFragment
import com.io.gazette.news.sportsNews.SportsNewsFragment
import com.io.gazette.news.worldNews.WorldNewsFragment

class NewsFragmentsViewPagerAdapter(hostFragment:Fragment) : FragmentStateAdapter(hostFragment) {
    private val fragments = listOf(
        WorldNewsFragment(),
        BusinessNewsFragment(),
        HealthNewsFragment(),
        SportsNewsFragment()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}