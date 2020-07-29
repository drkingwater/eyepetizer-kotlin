package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.pxq.eyepetizer.home.HomeViewModel
import me.pxq.eyepetizer.home.HomeViewModelFactory
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 推荐
 * Author : pxq
 * Date : 2020/7/27 10:30 PM
 */
class RecommendFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel> { HomeViewModelFactory.get(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        recyclerView.adapter = IndexRvAdapter().also {
            subscribeUi(it)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchHomeData()
    }

    private fun subscribeUi(adapter: IndexRvAdapter) {
        viewModel.homeData.observe(requireActivity(), Observer {
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