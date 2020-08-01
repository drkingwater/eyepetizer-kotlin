package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.pxq.eyepetizer.home.HomeViewModel
import me.pxq.eyepetizer.home.HomeViewModelFactory
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.eyepetizer.home.databinding.HomeFragmentRecommendBinding
import me.pxq.eyepetizer.home.decoration.MarginDecoration
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge

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
        }
        //请求数据
        viewModel.fetchHomeData()
    }


    /**
     * 观察数据变化
     */
    private fun subscribeUi(adapter: IndexRvAdapter) {
        logd("subscribe")
        viewModel.homeData.observe(requireActivity(), Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            when (it) {
                is ApiResult.Success -> {
                    adapter.items = it.data.itemList
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