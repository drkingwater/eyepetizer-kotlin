package me.pxq.eyepetizer.community.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.pxq.common.ApiService
import me.pxq.common.R
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.community.adapters.RecommendAdapter
import me.pxq.eyepetizer.community.ui.CommunityViewModel
import me.pxq.eyepetizer.community.ui.CommunityViewModelFactory
import me.pxq.network.ApiResult
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.ui.decoration.MarginDecoration

/**
 * Description: 社区-推荐
 * Author : pxq
 * Date : 2020/8/14 3:52 PM
 */
class RecommendFragment : BaseFragment() {

    private val recommendViewModel by activityViewModels<CommunityViewModel> { CommunityViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRvWithFreshBinding.inflate(inflater, container, false).run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = recommendViewModel
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                //设置分割线
                addItemDecoration(MarginDecoration(bottom = context.resources.getDimension(R.dimen.rv_divider_bottom).dp2px.toInt()))
                //设置adapter
                adapter = RecommendAdapter(recommendViewModel).also {
                    subscribeUI(it)
                }
//                setOnBottomListener {
//
//                }
            }
            // 设置swipe_layout边距
            with(refreshLayout){
                val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.leftMargin = 0
                layoutParams.rightMargin = 0
                this.layoutParams = layoutParams
            }
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logd("onViewCreated")
        // 获取推荐数据
        recommendViewModel.fetchData()
    }

    /**
     * 观察数据变化
     */
    private fun subscribeUI(adapter: RecommendAdapter) {
        recommendViewModel.recommendData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    adapter.items.clear()
                    adapter.items.addAll(it.data.itemList)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecommendFragment()
    }

}