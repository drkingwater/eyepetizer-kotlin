package me.pxq.eyepetizer.community.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import me.pxq.common.adapters.IvBannerAdapter
import me.pxq.common.adapters.IvBannerAdapter2
import me.pxq.common.data.Item
import me.pxq.common.databinding.RvItemHorScrollcardBinding
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.R
import me.pxq.eyepetizer.community.databinding.CommunityRvItemHorScrollCardBinding
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.ui.decoration.LeftDecoration
import me.pxq.utils.ui.decoration.MarginDecoration
import kotlin.math.sign

/**
 * Description: adapter for [me.pxq.eyepetizer.community.ui.recommend.RecommendFragment]
 * Author : pxq
 * Date : 2020/8/15 4:31 PM
 */
class RecommendAdapter(
    private val actionVM: BaseViewModel,
    val items: MutableList<Item> = mutableListOf()
) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]

        return when {
            item.type == "horizontalScrollCard" && item.data.dataType == "ItemCollection" -> 1
            item.type == "horizontalScrollCard" && item.data.dataType == "HorizontalScrollCard" -> 2
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        val layoutId = when(viewType){
            1 -> R.layout.community_rv_item_hor_scroll_card
            2 -> me.pxq.common.R.layout.rv_item_hor_scrollcard
            else -> me.pxq.common.R.layout.rv_item_hor_scrollcard
        }
        return RecommendHolder(
            DataBindingUtil.inflate(LayoutInflater.from(
                parent.context
            ), layoutId, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RecommendHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            when (binding) {
                is CommunityRvItemHorScrollCardBinding -> {   // horizontalScrollCard - ItemCollection
                    with(binding.rvHorScrollCard) {
                        adapter ?: kotlin.run {
                            setHasFixedSize(true)
                            // 设置边距
                            addItemDecoration(LeftDecoration(5f.dp2px.toInt()))
                            //
                            layoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                            adapter = HorizontalScrollCardAdapter(actionVM)
                        }
                        with((adapter as HorizontalScrollCardAdapter)) {
                            submitList(item.data.itemList)
                        }
                    }
                    binding.executePendingBindings()
                }
                is RvItemHorScrollcardBinding -> {  //水平滚动banner horizontalScrollCard - HorizontalScrollCard
                    with(binding) {
                        if (rvBanner.adapter == null) {
                            rvBanner.run {
                                parent.requestDisallowInterceptTouchEvent(true)
                                //设置分割线
                                addItemDecoration(LeftDecoration(5f.dp2px.toInt()))

                                orientation = ViewPager2.ORIENTATION_HORIZONTAL
//                                //布局方式
//                                layoutManager = LinearLayoutManager(
//                                    this.context,
//                                    RecyclerView.HORIZONTAL,
//                                    false
//                                )
//                                //优化绘制
//                                setHasFixedSize(true)
                                //设置adapter
                                adapter = IvBannerAdapter2(actionVM)
                                offscreenPageLimit = 2
                            }
                        }
                        //更新数据
                        with(rvBanner.adapter as IvBannerAdapter2){
                            this.items.clear()
                            this.items.addAll(item.data.itemList)
                            notifyDataSetChanged()
                        }
                        executePendingBindings()
                    }
                }
            }
        }
    }
}