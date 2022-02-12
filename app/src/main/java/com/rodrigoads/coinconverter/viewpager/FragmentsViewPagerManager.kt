package com.rodrigoads.coinconverter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rodrigoads.coinconverter.fragments.welcome.FirstWelcomeFragment
import com.rodrigoads.coinconverter.fragments.welcome.SecondWelcomeFragment
import com.rodrigoads.coinconverter.fragments.welcome.ThirdWelcomeFragment


class FragmentsViewPagerManager(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    val fragments: List<Fragment> =
        listOf(FirstWelcomeFragment(), SecondWelcomeFragment(), ThirdWelcomeFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
