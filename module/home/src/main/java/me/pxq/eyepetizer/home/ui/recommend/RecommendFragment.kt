package me.pxq.eyepetizer.home.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import me.pxq.eyepetizer.home.BaseFragment
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.eyepetizer.home.databinding.HomeFragmentRecommendBinding
import me.pxq.utils.ui.decoration.MarginDecoration
import me.pxq.network.ApiResult
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.loge
import me.pxq.utils.logi

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

    private lateinit var binding: HomeFragmentRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return HomeFragmentRecommendBinding.inflate(inflater, container, false).run {
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
//            addOnScrollListener(object : OnScrollListener() {
//                private var onBottom = false
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE && onBottom) {
//                        logi("到底了,刷新数据...")
//
//                    }
//                }
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val layoutManager =
//                        recyclerView.layoutManager as LinearLayoutManager
//                    //屏幕中最后一个可见子项的 position
//                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//                    //当前屏幕所看到的子项个数
//                    val visibleItemCount = layoutManager.childCount
//                    //当前 RecyclerView 的所有子项个数
//                    val totalItemCount = layoutManager.itemCount
//                    //RecyclerView 的滑动状态
//                    onBottom = visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1
//                }
//            })
        }
        //请求数据
        viewModel.fetchRecommend()
    }


    /**
     * 观察数据变化
     */
    private fun subscribeUi(adapter: IndexRvAdapter) {
        logd("subscribe")
        //刷新数据
        viewModel.homeData.observe(requireActivity(), Observer {
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
        viewModel.refreshData.observe(requireActivity(), Observer {
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
        viewModel.videoDetail.observe(requireActivity(), Observer {
            navigateToVideo(it)
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RecommendFragment()
    }
}