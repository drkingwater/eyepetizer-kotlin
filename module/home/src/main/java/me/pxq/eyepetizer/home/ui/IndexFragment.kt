package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.pxq.common.router.RouterHub
import me.pxq.common.ui.BaseMainFragment
import me.pxq.common.ui.view.FontCache
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.adapters.ViewPagerAdapter
import me.pxq.utils.logd

/**
 * Description: 首页fragment
 * Author : pxq
 * Date : 2020/7/20 9:56 PM
 */
@Route(path = RouterHub.MAIN_HONE)
class IndexFragment : BaseMainFragment() {

    private val tabs = listOf("发现", "推荐", "日报")

    override fun createAdapter() = ViewPagerAdapter(this, tabs.size)

    override fun createTabs() = tabs

    override fun currentIndex() = 1
}