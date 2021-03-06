package me.pxq.eyepetizer.community.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.pxq.framework.R
import me.pxq.framework.databinding.FragmentRvWithFreshBinding
import me.pxq.framework.ui.BaseFragment
import me.pxq.eyepetizer.community.adapters.RecommendAdapter
import me.pxq.eyepetizer.community.viewmodels.CommunityViewModel
import me.pxq.eyepetizer.community.viewmodels.CommunityViewModelFactory
import me.pxq.utils.ui.decoration.MarginDecoration
import me.pxq.utils.ui.decoration.StaggeredDecoration

/**
 * Description: 社区-推荐
 * Author : pxq
 * Date : 2020/8/14 3:52 PM
 */
@ExperimentalCoroutinesApi
class RecommendFragment : BaseFragment() {

    // 推荐vm
    private val recommendViewModel by activityViewModels<CommunityViewModel> { CommunityViewModelFactory() }

    // 计算瀑布流图片宽度:(屏幕宽度-item左右边距-中间边距) / 2
    private val staggeredLayoutItemWidth by lazy {
        ((requireContext().resources.displayMetrics.widthPixels - 2 * requireContext().resources.getDimension(
            R.dimen.header_padding
        ) - requireContext().resources.getDimension(R.dimen.hor_scroll_banner_divider_width)
            .toInt()) / 2).toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRvWithFreshBinding.inflate(inflater, container, false).run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = recommendViewModel
            with(recyclerView) {
//                layoutManager = LinearLayoutManager(requireContext())
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                //设置分割线
                addItemDecoration(
                    MarginDecoration(
                        top = context.resources.getDimension(R.dimen.first_item_margin_top)
                            .toInt(),
                        bottom = context.resources.getDimension(R.dimen.rv_divider_bottom)
                            .toInt()
                    )
                )
                addItemDecoration(
                    StaggeredDecoration(
                        context.resources.getDimension(R.dimen.header_padding).toInt(),
                        context.resources.getDimension(R.dimen.header_padding).toInt(),
                        context.resources.getDimension(R.dimen.hor_scroll_banner_divider_width)
                            .toInt()
                    )
                )
                //设置adapter
                adapter = RecommendAdapter(recommendViewModel, staggeredLayoutItemWidth).also {
                    subscribeUI(it)
                }
                setOnBottomListener {
                    recommendViewModel.fetchNext()
                }
            }
            root
        }
    }

    override fun fetchData() {
        // 获取推荐数据
        recommendViewModel.fetchData()
    }

    /**
     * 观察数据变化
     */
    private fun subscribeUI(adapter: RecommendAdapter) {
        // 首次加载数据
        recommendViewModel.recommendData.observe(viewLifecycleOwner) {
            adapter.items.clear()
            adapter.items.addAll(it.itemList)
            adapter.notifyDataSetChanged()
        }
        // 加载更多
        recommendViewModel.nextData.observe(viewLifecycleOwner) {
            val index = adapter.items.size
            adapter.items.addAll(it.itemList)
            adapter.notifyItemRangeInserted(index, adapter.items.size)
        }
        // 点击图集
        recommendViewModel.albumDetail.observe(viewLifecycleOwner) {
            navigateToAlbum(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecommendFragment()
    }

}