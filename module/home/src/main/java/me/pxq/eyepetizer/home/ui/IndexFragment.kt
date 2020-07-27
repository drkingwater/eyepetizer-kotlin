package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.ui.vp.ViewPagerAdapter
import me.pxq.utils.logd
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener as OnTabSelectedListener1
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener as OnTabSelectedListener2

/**
 * Description: 首页fragment
 * Author : pxq
 * Date : 2020/7/20 9:56 PM
 */
@Route(path = RouterHub.MAIN_HONE)
class IndexFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_index, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById<ViewPager>(R.id.index_view_pager).apply {
            adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
            currentItem = 1
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

            //绑定ViewPager
            setupWithViewPager(viewPager, false)
        }

    }

}