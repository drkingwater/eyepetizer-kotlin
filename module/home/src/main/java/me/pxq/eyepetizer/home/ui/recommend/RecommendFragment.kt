package me.pxq.eyepetizer.home.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.utils.ui.decoration.MarginDecoration
import me.pxq.network.ApiResult
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 推荐
 * Author : pxq
 * Date : 2020/7/27 10:30 PM
 */
class RecommendFragment : BaseFragment() {

    private val viewModel by activityViewModels<RecommendViewModel> {
        RecommendViewModelFactory.get(
            requireContext()
        )
    }

    private lateinit var binding: FragmentRvWithFreshBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRvWithFreshBinding.inflate(inflater, container, false).run {
            binding = this
            viewModel = this@RecommendFragment.viewModel
            lifecycleOwner = requireActivity()
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            //设置分割线
            addItemDecoration(MarginDecoration(bottom = context.resources.getDimension(me.pxq.common.R.dimen.rv_divider_bottom).dp2px.toInt()))
            //设置adapter
            adapter = IndexRvAdapter(viewModel).also {
                subscribeUi(it)
            }

            setOnBottomListener {
                viewModel.fetchNextPage()
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
        viewModel.homeData.observe(viewLifecycleOwner, Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            when (it) {
                is ApiResult.Success -> {
                    // 隐藏网络请求失败布局
                    binding.layoutNetError.visibility = View.GONE
                    // 显示数据
                    binding.refreshLayout.visibility = View.VISIBLE
                    adapter.items.clear()
                    adapter.items.addAll(it.data.itemList)
                    adapter.notifyDataSetChanged()
                }
                is ApiResult.Error -> {
                    // 请求失败
                    loge(it.exception.message ?: "error!!!")
                    // 隐藏布局
                    binding.refreshLayout.visibility = View.GONE
                    // 显示网络错误
                    binding.layoutNetError.visibility = View.VISIBLE
                }
            }
        })
        //上拉加载数据
        viewModel.refreshData.observe(viewLifecycleOwner, Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            when (it) {
                is ApiResult.Success -> {
                    // 隐藏网络请求失败布局
                    binding.layoutNetError.visibility = View.GONE
                    // 显示数据
                    binding.refreshLayout.visibility = View.VISIBLE

                    val newPosition = adapter.items.size
                    adapter.items.addAll(it.data.itemList)
                    adapter.notifyItemInserted(newPosition)
                }
                is ApiResult.Error -> {

                    // 请求失败
                    loge(it.exception.message ?: "error!!!")
                    // 隐藏布局
                    binding.refreshLayout.visibility = View.GONE
                    // 显示网络错误
                    binding.layoutNetError.visibility = View.VISIBLE
                }
            }
        })
        // 导航到详情页
        viewModel.videoDetail.observe(viewLifecycleOwner, Observer {
            navigateToVideo(it)
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RecommendFragment()
    }
}