package me.pxq.eyepetizer.community.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.pxq.common.R
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.community.adapters.FollowAdapter
import me.pxq.utils.ui.decoration.MarginDecoration

/**
 * Description: 社区-关注
 * Author : pxq
 * Date : 2020/8/14 3:53 PM
 */
@ExperimentalCoroutinesApi
class FollowFragment : BaseFragment() {

    private val followViewModel by activityViewModels<FollowViewModel> { FollowViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRvWithFreshBinding.inflate(inflater, container, false).run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = followViewModel
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                //设置分割线
                addItemDecoration(
                    MarginDecoration(
                        left = context.resources.getDimension(R.dimen.header_padding)
                            .toInt(),
                        top = context.resources.getDimension(R.dimen.first_item_margin_top)
                            .toInt(),
                        right = context.resources.getDimension(R.dimen.header_padding)
                            .toInt(),
                        bottom = context.resources.getDimension(R.dimen.rv_divider_bottom)
                            .toInt()
                    )
                )
                //设置adapter
                adapter = FollowAdapter(followViewModel).also {
                    subscribeUI(it)
                }
                setOnBottomListener {
                    followViewModel.fetchNext()
                }
            }
            // 设置swipe_layout边距
            with(refreshLayout) {
                val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.leftMargin = 0
                layoutParams.rightMargin = 0
                this.layoutParams = layoutParams
            }
            root
        }
    }

    override fun fetchData() {
        // 首次加载数据
        followViewModel.fetchData()
    }

    /**
     * 观察数据变化
     */
    private fun subscribeUI(adapter: FollowAdapter) {
        followViewModel.followData.observe(viewLifecycleOwner) {
            adapter.items.clear()
            adapter.items.addAll(it.itemList)
            adapter.notifyDataSetChanged()
        }
        followViewModel.nextData.observe(viewLifecycleOwner) {
            val index = adapter.items.size
            adapter.items.addAll(it.itemList)
            adapter.notifyItemRangeInserted(index, adapter.items.size)
        }
        // 跳转播放页
        followViewModel.videoDetail.observe(viewLifecycleOwner) { video ->
            navigateToVideo(video)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FollowFragment()
    }
}