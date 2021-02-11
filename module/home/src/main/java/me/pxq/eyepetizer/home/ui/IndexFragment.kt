package me.pxq.eyepetizer.home.ui

import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.framework.router.RouterHub
import me.pxq.framework.ui.BaseTabsFragment
import me.pxq.eyepetizer.home.adapters.ViewPagerAdapter

/**
 * Description: 首页fragment
 * Author : pxq
 * Date : 2020/7/20 9:56 PM
 */
@Route(path = RouterHub.MAIN_HONE)
class IndexFragment : BaseTabsFragment() {

    private val tabs = listOf("发现", "推荐", "日报")

    override fun createAdapter() = ViewPagerAdapter(this, tabs.size)

    override fun createTabs() = tabs

    override fun currentIndex() = 1
}