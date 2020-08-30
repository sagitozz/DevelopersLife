package com.application.developerslife.ui

import android.os.Bundle
import android.view.View
import com.application.developerslife.R
import com.application.developerslife.mvp.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_pages.*

/**
 * @autor d.snytko
 */
class PagerFragment : BaseFragment(R.layout.fragment_pages) {

    private val pagerAdapter by lazy { CategoryPagerAdapter(this) }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager, fun(tab: TabLayout.Tab, position: Int) {
            tab.text = CategoryPagerAdapter.GifCategory.values()[position].toString()
        }).attach()
    }
}
