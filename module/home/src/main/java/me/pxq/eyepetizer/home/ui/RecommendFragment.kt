package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import me.pxq.eyepetizer.home.HomeViewModel
import me.pxq.eyepetizer.home.HomeViewModelFactory
import me.pxq.eyepetizer.home.R
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 推荐
 * Author : pxq
 * Date : 2020/7/27 10:30 PM
 */
class RecommendFragment : Fragment() {

    val viewModel by activityViewModels<HomeViewModel> { HomeViewModelFactory.get(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_recommend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.homeData.observe(requireActivity(), Observer {
            when (it) {
                is ApiResult.Success -> logd(it.data)
                is ApiResult.Error -> loge(it.exception.message ?: "error!!!")
            }
        })
        viewModel.fetchHomeData()
    }


    companion object {
        @JvmStatic
        fun newInstance() = RecommendFragment()
    }
}