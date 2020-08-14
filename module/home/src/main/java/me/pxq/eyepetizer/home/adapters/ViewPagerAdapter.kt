package me.pxq.eyepetizer.home.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.pxq.eyepetizer.home.ui.daily.DailyFragment
import me.pxq.eyepetizer.home.ui.discovery.DiscoveryFragment
import me.pxq.eyepetizer.home.ui.recommend.RecommendFragment

/**
 * Description: 首页viewpager2适配器[me.pxq.eyepetizer.home.ui.IndexFragment]
 * Author : pxq
 * Date : 2020/7/27 10:35 PM
 */
class ViewPagerAdapter(fragment: Fragment, private val tabSize : Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = tabSize

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiscoveryFragment.newInstance()
            1 -> RecommendFragment.newInstance()
            2 -> DailyFragment.newInstance()
            else -> throw IndexOutOfBoundsException()
        }
    }
}