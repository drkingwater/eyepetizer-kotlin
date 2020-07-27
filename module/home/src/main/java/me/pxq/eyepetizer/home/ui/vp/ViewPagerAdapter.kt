package me.pxq.eyepetizer.home.ui.vp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import me.pxq.eyepetizer.home.ui.DailyFragment
import me.pxq.eyepetizer.home.ui.DiscoveryFragment
import me.pxq.eyepetizer.home.ui.RecommendFragment

/**
 * Description: vp适配器
 * Author : pxq
 * Date : 2020/7/27 10:35 PM
 */
class ViewPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> DiscoveryFragment.newInstance()
            1 -> RecommendFragment.newInstance()
            2 -> DailyFragment.newInstance()
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "发现"
            1 -> "推荐"
            2 -> "日报"
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getCount(): Int = 3
}