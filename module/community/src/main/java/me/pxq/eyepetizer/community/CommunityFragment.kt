package me.pxq.eyepetizer.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.pxq.common.R
import me.pxq.common.router.RouterHub
import me.pxq.common.ui.BaseFragment
import me.pxq.common.ui.BaseMainFragment
import me.pxq.eyepetizer.community.adapters.ViewPagerAdapter

/**
 * Description: 底部导航：社区
 * Author : pxq
 * Date : 2020/8/14 3:18 PM
 */
@Route(path = RouterHub.MAIN_COMMUNITY)
class CommunityFragment : BaseMainFragment() {

    private val tabs = listOf("推荐", "关注")

    override fun createAdapter() = ViewPagerAdapter(this, tabs.size)

    override fun createTabs() = tabs
    // 选中推荐
    override fun currentIndex() = 0
}