package me.pxq.eyepetizer.notification.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import me.pxq.common.R
import me.pxq.common.databinding.FragmentRvWithFreshBinding
import me.pxq.common.ui.BaseFragment
import me.pxq.eyepetizer.notification.adapters.PushAdapter
import me.pxq.eyepetizer.notification.viewmodels.PushViewModel
import me.pxq.eyepetizer.notification.viewmodels.PushViewModelFactory
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.ui.decoration.MarginDecoration

/**
 * Description: 通知-推送Fragment
 * Author : pxq
 * Date : 2020/8/23 4:30 PM
 */
class PushFragment : BaseFragment() {

    private val pushViewModel by activityViewModels<PushViewModel> {
        PushViewModelFactory()
    }

    private lateinit var binding: FragmentRvWithFreshBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRvWithFreshBinding.inflate(inflater, container, false).run {
            binding = this
            viewModel = pushViewModel
            lifecycleOwner = viewLifecycleOwner
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context)
                //设置分割线
                addItemDecoration(
                    MarginDecoration(
                        left = context.resources.getDimension(R.dimen.header_padding)
                            .toInt(),
                        top = context.resources.getDimension(R.dimen.first_item_margin_top_push)
                            .toInt(),
                        right = context.resources.getDimension(R.dimen.header_padding)
                            .toInt(),
                        bottom = context.resources.getDimension(R.dimen.rv_divider_bottom)
                            .toInt()
                    )
                )

                //设置adapter
                adapter = PushAdapter(pushViewModel).also {
                    observe(it)
                }

                setOnBottomListener {
                    pushViewModel.fetchNext()
                }
            }
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 请求数据
        pushViewModel.fetchData()
    }


    private fun observe(adapter: PushAdapter) {
        pushViewModel.pushData.observe(viewLifecycleOwner) {
            adapter.messages.clear()
            adapter.messages.addAll(it.messageList)
            adapter.notifyDataSetChanged()
        }
        pushViewModel.nextData.observe(viewLifecycleOwner) {
            val start = adapter.messages.size
            adapter.messages.addAll(it.messageList)
            adapter.notifyItemRangeInserted(start, adapter.messages.size)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = PushFragment()
    }

}