package me.pxq.eyepetizer.home.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.home.adapters.IndexRvAdapter
import me.pxq.eyepetizer.home.viewmodels.DailyViewModel
import me.pxq.eyepetizer.home.viewmodels.DailyViewModelFactory
import me.pxq.utils.ui.decoration.MarginDecoration
import me.pxq.utils.logd
import me.pxq.utils.loge
import me.pxq.utils.logi

/**
 * Description: 推荐
 * Author : pxq
 * Date : 2020/7/27 10:30 PM
 */
@ExperimentalCoroutinesApi
class DailyFragment : BaseFragment() {

    private val viewModel by activityViewModels<DailyViewModel> {
        DailyViewModelFactory.get(
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
            viewModel = this@DailyFragment.viewModel
            lifecycleOwner = requireActivity()

            binding.recyclerView.run {
                layoutManager = LinearLayoutManager(requireContext())
                //设置分割线
                addItemDecoration(
                    MarginDecoration(
                        top = context.resources.getDimension(me.pxq.common.R.dimen.header_padding)
                            .toInt(),
                        bottom = context.resources.getDimension(me.pxq.common.R.dimen.rv_divider_bottom)
                            .toInt()
                    )
                )
                //设置adapter
                adapter = IndexRvAdapter(this@DailyFragment.viewModel).also {
                    subscribeUi(it)
                }
                setOnBottomListener {
                    this@DailyFragment.viewModel.fetchNext()
                }
            }

            root
        }
    }

    override fun fetchData() {
        //请求数据
        viewModel.fetchData()
    }


    /**
     * 观察数据变化
     */
    private fun subscribeUi(adapter: IndexRvAdapter) {
        logd("subscribe")
        //刷新数据
        viewModel.dailyData.observe(viewLifecycleOwner, Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            adapter.items.clear()
            adapter.items.addAll(it.itemList)
            adapter.notifyDataSetChanged()
        })
        //上拉加载数据
        viewModel.nextData.observe(viewLifecycleOwner, Observer {
            loge("data change...")
            binding.refreshLayout.isRefreshing = false
            val newPosition = adapter.items.size
            adapter.items.addAll(it.itemList)
            adapter.notifyItemInserted(newPosition)
        })
        // 导航到详情页
        viewModel.videoDetail.observe(requireActivity(), Observer {
            navigateToVideo(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = DailyFragment()
    }

}