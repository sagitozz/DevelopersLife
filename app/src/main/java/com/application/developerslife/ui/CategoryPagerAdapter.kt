package com.application.developerslife.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @autor d.snytko
 */
class CategoryPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = GifCategory.values().size

    override fun createFragment(position: Int): Fragment =
        FragmentGifPage.newInstance(GifCategory.values()[position])

    enum class GifCategory(private val value: String) {
        LATEST("ПОСЛЕДНИЕ"),
        TOP("ЛУЧШИЕ"),
        HOT("ГОРЯЧИЕ");

        override fun toString() = value
    }
}
