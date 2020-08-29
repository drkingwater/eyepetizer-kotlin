package me.pxq.eyepetizer.home.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.eyepetizer.home.viewmodels.DiscoveryViewModel
import me.pxq.eyepetizer.home.viewmodels.DiscoveryViewModelFactory
import me.pxq.utils.ui.decoration.MarginDecoration
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 发现
 * Author : pxq
 * Date : 2020/7/27 10:28 PM
 */
@ExperimentalCoroutinesApi
class DiscoveryFragment : BaseFragment() {

    private val viewModel by activityViewModels<DiscoveryViewModel> {
        DiscoveryViewModelFactory.get(requireContext())
    }

    private lateinit var binding: FragmentRvWithFreshBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRvWithFreshBinding.inflate(inflater, container, false).run {
            binding = this
            viewModel = this@DiscoveryFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            // 为了水平滑动Banner，取消左右margin
            with(refreshLayout) {
                val layoutParams = layoutParams as ConstraintLayout.LayoutParams
                layoutParams.leftMargin = 0
                layoutParams.rightMargin = 0
                this.layoutParams = layoutParams
            }

            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            //设置分割线
            addItemDecoration(
                MarginDecoration(
                    top = context.resources.getDimension(me.pxq.common.R.dimen.header_padding)
                        .toInt(),
                    bottom = context.resources.getDimension(me.pxq.common.R.dimen.rv_divider_bottom)
                        .toInt()
                )
            )
            //设置adapter
            adapter = IndexRvAdapter(viewModel).also {
                subscribeUi(it)
            }
        }
        //请求数据
        viewModel.fetchData()
    }


    /**
     * 观察数据变化
     */
    private fun subscribeUi(adapter: IndexRvAdapter) {
        logd("subscribe")
        //刷新数据
        viewModel.discoveryData.observe(viewLifecycleOwner, Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            adapter.items.clear()
            adapter.items.addAll(it.itemList)
            adapter.notifyDataSetChanged()
        })
        // 导航到详情页
        viewModel.videoDetail.observe(viewLifecycleOwner, Observer {
            navigateToVideo(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DiscoveryFragment()
    }

}