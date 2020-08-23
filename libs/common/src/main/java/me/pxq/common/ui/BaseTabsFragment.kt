package me.pxq.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.pxq.common.R

/**
 * Description: 主页Fragment布局一样，抽象类
 * Author : pxq
 * Date : 2020/8/23 4:39 PM
 */
abstract class BaseTabsFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_vp, container, false).apply {
            viewPager = findViewById<ViewPager2>(R.id.index_view_pager).apply {
                adapter = createAdapter()
            }
            tabLayout = findViewById<TabLayout>(R.id.index_tab_layout).apply {
                //指示器高度
                setSelectedTabIndicator(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_tab_indicator
                    )
                )
                //设置指示器的颜色
                setSelectedTabIndicatorColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorTextPrimary
                    )
                )
                //设置文字颜色
                setTabTextColors(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorTextSecondary
                    ),
                    ContextCompat.getColor(requireContext(), R.color.colorTextPrimary)
                )
            }

            // 1、tablayout绑定viewpager
            TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
            // 2、移除所有tab
            tabLayout.removeAllTabs()
            // 3、手动添加tabs
            with(tabLayout) {
                for (tab in createTabs()) {
                    addTab(
                        newTab().apply {
                            customView = LayoutInflater.from(requireContext())
                                .inflate(R.layout.tab_item, this@with, false).also {
                                    it.findViewById<TextView>(R.id.tv_tab_title).text = tab
                                }
                        })
                }
            }
            //选中"推送"
            viewPager.currentItem = currentIndex()
        }
    }

    // vp2 adapter
    abstract fun createAdapter(): FragmentStateAdapter

    abstract fun createTabs(): List<String>

    abstract fun currentIndex(): Int
}