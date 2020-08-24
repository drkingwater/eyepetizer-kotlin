package me.pxq.eyepetizer.community.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import me.pxq.common.adapters.IvBannerAdapter
import me.pxq.common.model.Item
import me.pxq.common.databinding.RvItemHorScrollcardBinding
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.R
import me.pxq.eyepetizer.community.databinding.CommunityRvItemColumnsCardBinding
import me.pxq.eyepetizer.community.databinding.CommunityRvItemHorScrollCardBinding
import me.pxq.utils.ui.decoration.LeftDecoration

/**
 * Description: adapter for [me.pxq.eyepetizer.community.ui.recommend.RecommendFragment]
 * [staggeredLayoutItemWidth]:瀑布流Item宽度（粗略计算）,用来计算瀑布流图片高度
 * Author : pxq
 * Date : 2020/8/15 4:31 PM
 */
class RecommendAdapter(
    private val actionVM: BaseViewModel,
    private val staggeredLayoutItemWidth: Int,
    val items: MutableList<Item> = mutableListOf()
) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]

        return when {
            item.type == "horizontalScrollCard" && item.data.dataType == "ItemCollection" -> 1
            item.type == "horizontalScrollCard" && item.data.dataType == "HorizontalScrollCard" -> 2
            item.type == "communityColumnsCard" && item.data.dataType == "FollowCard" -> 3
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        val layoutId = when (viewType) {
            1 -> R.layout.community_rv_item_hor_scroll_card
            2 -> me.pxq.common.R.layout.rv_item_hor_scrollcard
            3 -> R.layout.community_rv_item_columns_card
            else -> me.pxq.common.R.layout.rv_item_hor_scrollcard
        }
        return RecommendHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), layoutId, parent, false
            )
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
                    // 设置跨行
                    (itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                        true
                    with(binding.rvHorScrollCard) {
                        adapter ?: kotlin.run {
                            setHasFixedSize(true)
                            // 设置边距
                            addItemDecoration(
                                LeftDecoration(
                                    context.resources.getDimension(me.pxq.common.R.dimen.hor_scroll_banner_divider_width)
                                        .toInt()
                                )
                            )
                            //
//                            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

                            adapter = HorizontalScrollCardAdapter(actionVM)
                        }
                        with((adapter as HorizontalScrollCardAdapter)) {
                            submitList(item.data.itemList)
                        }
                    }
                    binding.executePendingBindings()
                }
                is RvItemHorScrollcardBinding -> {  //水平滚动banner horizontalScrollCard - HorizontalScrollCard
                    // 设置跨行
                    (itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                        true
                    with(binding) {
                        with(rvBanner) {
                            adapter ?: kotlin.run {
                                adapter = IvBannerAdapter(actionVM)
                            }
                            offscreenPageLimit = 1
                        }
                        (rvBanner.adapter as IvBannerAdapter).submitList(item.data.itemList){
                            rvBanner.setCurrentItem(0, false)
                        }
                        executePendingBindings()
                    }
                }
                is CommunityRvItemColumnsCardBinding -> {
                    // 重写ImageView高度
                    binding.ivCover.run {
                        layoutParams.height = calImageViewHeight(
                            item.data.content.data.width,
                            item.data.content.data.height
                        )
                    }
                    binding.column = item
                    binding.executePendingBindings()
                }
            }
        }
    }

    /**
     * 根据图片尺寸计算ImageView高度
     * [imageWidth]:图片宽度
     * [imageHeight]:图片高度
     */
    private fun calImageViewHeight(imageWidth: Int, imageHeight: Int) =
        if (imageHeight > 0 && imageWidth > 0) {
            staggeredLayoutItemWidth * imageHeight / imageWidth
        } else {
            staggeredLayoutItemWidth
        }
}