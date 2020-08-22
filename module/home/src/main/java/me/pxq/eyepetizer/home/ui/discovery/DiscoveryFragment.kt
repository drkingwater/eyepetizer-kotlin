package me.pxq.eyepetizer.home.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.utils.ui.decoration.MarginDecoration
import me.pxq.network.ApiResult
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.loge
import me.pxq.utils.logi

/**
 * Description: 发现
 * Author : pxq
 * Date : 2020/7/27 10:28 PM
 */
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
            with(refreshLayout){
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
            addItemDecoration(MarginDecoration(top = context.resources.getDimension(me.pxq.common.R.dimen.header_padding)
                .toInt(),
                bottom = context.resources.getDimension(me.pxq.common.R.dimen.rv_divider_bottom)
                    .toInt()))
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
            when (it) {
                is ApiResult.Success -> {
                    adapter.items.clear()
                    adapter.items.addAll(it.data.itemList)
                    adapter.notifyDataSetChanged()
                }
                is ApiResult.Error -> loge(it.exception.message ?: "error!!!")
            }
        })
        //上拉加载数据
//        viewModel.refreshData.observe(requireActivity(), Observer {
//            loge("data change...")
//            binding.refreshLayout.isRefreshing = false
//            when (it) {
//                is ApiResult.Success -> {
//                    adapter.items.addAll(it.data.itemList)
//                    adapter.notifyDataSetChanged()
//                }
//                is ApiResult.Error -> loge(it.exception.message ?: "error!!!")
//            }
//        })
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