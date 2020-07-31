package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.pxq.common.router.RouterHub
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
class IndexFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val tabs = listOf("发现", "推荐", "日报")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_index, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById<ViewPager2>(R.id.index_view_pager).apply {
            adapter =
                ViewPagerAdapter(this@IndexFragment)
        }
        tabLayout = view.findViewById<TabLayout>(R.id.index_tab_layout).apply {
            //指示器高度
            setSelectedTabIndicator(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.home_shape_tab_indicator
                )
            )
            //设置指示器的颜色
            setSelectedTabIndicatorColor(
                ContextCompat.getColor(
                    requireContext(),
                    me.pxq.common.R.color.colorTextPrimary
                )
            )
            //设置文字颜色
            setTabTextColors(
                ContextCompat.getColor(
                    requireContext(),
                    me.pxq.common.R.color.colorTextSecondary
                ), ContextCompat.getColor(requireContext(), me.pxq.common.R.color.colorTextPrimary)
            )

        }

        //tablayout绑定viewpager
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        //再移除所有tab
        tabLayout.removeAllTabs()
        //手动添加tabs
        with(tabLayout) {
            for (tab in tabs) {
                addTab(
                    newTab().apply {
                        customView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.home_tab_item, this@with, false).also {
                                it.findViewById<TextView>(R.id.tv_tab_title).text = tab
                            }
                    })
            }
        }
        //选中"推荐"
        viewPager.currentItem = 1

    }
}