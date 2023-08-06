package com.io.gazette.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.io.gazette.R

class HomeFragment : Fragment() {


    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.news_screens_view_pager)
        tabLayout = view.findViewById(R.id.news_tabs_layout)

        setUpViewPagerAndTabLayout()

    }


    private fun setUpViewPagerAndTabLayout() {
        val viewPagerAdapter = NewsFragmentsViewPagerAdapter(this)

        viewPager.adapter = viewPagerAdapter
        val tabTitles = listOf("World News", "Business News", "Health News", "Sports News")

        TabLayoutMediator(tabLayout, viewPager) { tabLayout, position ->
            tabLayout.text = tabTitles[position]
        }.attach()
    }

}