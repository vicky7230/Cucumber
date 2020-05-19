package com.vicky7230.cucumber.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vicky7230.cucumber.ui.home.likes.LikesFragment
import com.vicky7230.cucumber.ui.home.recipes.RecipesFragment
import com.vicky7230.cucumber.ui.home.search.SearchFragment

/**
 * Created by vicky on 11/2/18.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RecipesFragment.newInstance()
            1 -> LikesFragment.newInstance()
            2 -> SearchFragment.newInstance()
            else -> RecipesFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

    override fun getCount(): Int {
        return 3
    }
}