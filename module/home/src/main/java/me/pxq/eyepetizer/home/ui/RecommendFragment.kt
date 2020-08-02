package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import me.pxq.eyepetizer.home.HomeViewModel
import me.pxq.eyepetizer.home.HomeViewModelFactory
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.eyepetizer.home.databinding.HomeFragmentRecommendBinding
import me.pxq.eyepetizer.home.decoration.MarginDecoration
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge
import me.pxq.utils.logi

/**
 * Description: 推荐
 * Author : pxq
 * Date : 2020/7/27 10:30 PM
 */
class RecommendFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel> {
        HomeViewModelFactory.get(
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
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            //设置分割线
            addItemDecoration(MarginDecoration(bottom = 50))
            //设置adapter
            adapter = IndexRvAdapter().also {
                subscribeUi(it)
            }
            addOnScrollListener(object : OnScrollListener() {
                private var onBottom = false
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && onBottom){
                        logi("到底了,刷新数据...")
                        viewModel.fetchNextPage()
                    }
                }
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        recyclerView.layoutManager as LinearLayoutManager
                    //屏幕中最后一个可见子项的 position
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    //当前屏幕所看到的子项个数
                    val visibleItemCount = layoutManager.childCount
                    //当前 RecyclerView 的所有子项个数
                    val totalItemCount = layoutManager.itemCount
                    //RecyclerView 的滑动状态
                    val state = recyclerView.scrollState
                    onBottom = visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1
                }
            })
        }
        //请求数据
        viewModel.fetchHomeData()
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
                    adapter.items.clear()
                    adapter.items.addAll(it.data.itemList)
                    adapter.notifyDataSetChanged()
                }
                is ApiResult.Error -> loge(it.exception.message ?: "error!!!")
            }
        })
        //上拉加载数据
        viewModel.refreshData.observe(requireActivity(), Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            when (it) {
                is ApiResult.Success -> {
                    adapter.items.addAll(it.data.itemList)
                    adapter.notifyDataSetChanged()
                }
                is ApiResult.Error -> loge(it.exception.message ?: "error!!!")
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = RecommendFragment()
    }
}