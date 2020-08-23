package me.pxq.eyepetizer.notification


import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.common.router.RouterHub
import me.pxq.common.ui.BaseTabsFragment
import me.pxq.eyepetizer.notification.adapters.ViewPagerAdapter

/**
 * Description: 主页-通知Fragment
 * Author : pxq
 * Date : 2020/8/23 4:25 PM
 */
@Route(path = RouterHub.MAIN_NOTIFICATION)
class NotificationFragment : BaseTabsFragment() {

    private val tabs = listOf("推送", "互动", "私信")

    override fun createAdapter() = ViewPagerAdapter(this, tabs.size)

    override fun createTabs() = tabs
    // 选中推送
    override fun currentIndex() = 0

}