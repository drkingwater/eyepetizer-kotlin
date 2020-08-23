package me.pxq.eyepetizer.notification.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.pxq.eyepetizer.notification.ui.InteractFragment
import me.pxq.eyepetizer.notification.ui.PrivateFragment
import me.pxq.eyepetizer.notification.ui.PushFragment

/**
 * Description: 通知Vp适配器
 * Author : pxq
 * Date : 2020/8/23 4:29 PM
 */
class ViewPagerAdapter(fragment: Fragment, private val tabSize: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = tabSize

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PushFragment.newInstance()
            1 -> InteractFragment.newInstance()
            2 -> PrivateFragment.newInstance()
            else -> throw IndexOutOfBoundsException()
        }
    }
}