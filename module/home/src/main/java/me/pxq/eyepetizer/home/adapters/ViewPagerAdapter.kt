package me.pxq.eyepetizer.home.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.pxq.eyepetizer.home.ui.DailyFragment
import me.pxq.eyepetizer.home.ui.DiscoveryFragment
import me.pxq.eyepetizer.home.ui.RecommendFragment

/**
 * Description: 首页viewpager2适配器[me.pxq.eyepetizer.home.ui.IndexFragment]
 * Author : pxq
 * Date : 2020/7/27 10:35 PM
 */
class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiscoveryFragment.newInstance()
            1 -> RecommendFragment.newInstance()
            2 -> DailyFragment.newInstance()
            else -> throw IndexOutOfBoundsException()
        }
    }
}