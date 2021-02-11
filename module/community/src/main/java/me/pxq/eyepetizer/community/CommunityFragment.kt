package me.pxq.eyepetizer.community

import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.framework.router.RouterHub
import me.pxq.framework.ui.BaseTabsFragment
import me.pxq.eyepetizer.community.adapters.ViewPagerAdapter

/**
 * Description: 底部导航：社区
 * Author : pxq
 * Date : 2020/8/14 3:18 PM
 */
@Route(path = RouterHub.MAIN_COMMUNITY)
class CommunityFragment : BaseTabsFragment() {

    private val tabs = listOf("推荐", "关注")

    override fun createAdapter() = ViewPagerAdapter(this, tabs.size)

    override fun createTabs() = tabs
    // 选中推荐
    override fun currentIndex() = 0
}