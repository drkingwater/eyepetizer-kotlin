package me.pxq.launch.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import me.pxq.common.adapters.IvBannerAdapter
import me.pxq.eyepetizer.community.ui.CommunityViewModel
import me.pxq.eyepetizer.community.ui.CommunityViewModelFactory
import me.pxq.network.ApiResult

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/15 8:05 PM
 */
class Vp2Fragment : Fragment() {

    private val viewModel by activityViewModels<CommunityViewModel> { CommunityViewModelFactory() }

    lateinit var vp: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vp2, container, false).apply {
            vp = findViewById(R.id.vp)
            vp.offscreenPageLimit = 3
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recommendData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    vp.adapter = IvBannerAdapter(viewModel)
                    (vp.adapter as IvBannerAdapter).submitList(it.data.itemList[1].data.itemList)
                    vp.offscreenPageLimit = 3
                }
            }
        })
        viewModel.fetchData()
    }
}