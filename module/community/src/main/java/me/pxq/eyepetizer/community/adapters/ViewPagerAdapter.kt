package me.pxq.eyepetizer.community.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.pxq.eyepetizer.community.FollowFragment
import me.pxq.eyepetizer.community.RecommendFragment

/**
 * Description: 首页viewpager2适配器[me.pxq.eyepetizer.community.CommunityFragment]
 * Author : pxq
 * Date : 2020/8/14 15:54 PM
 */
class ViewPagerAdapter(fragment: Fragment, private val tabSize : Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = tabSize

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecommendFragment.newInstance()
            1 -> FollowFragment.newInstance()
            else -> throw IndexOutOfBoundsException()
        }
    }
}