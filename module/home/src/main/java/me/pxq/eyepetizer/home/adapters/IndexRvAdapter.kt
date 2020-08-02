package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import me.pxq.common.data.Item
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.databinding.*
import me.pxq.eyepetizer.home.decoration.MarginDecoration
import me.pxq.utils.logd

/**
 * Description: [me.pxq.eyepetizer.home.ui.IndexFragment] 推荐栏 Rv Adapter
 * Author : pxq
 * Date : 2020/7/29 9:57 PM
 */
class IndexRvAdapter(var items: List<Item> = emptyList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when {
            item.type == "textCard" && item.data.dataType == "TextCardWithRightAndLeftTitle" -> VIEW_HOLDER_TYPE_TEXT_CARD_TITLE
            item.type == "textCard" && item.data.dataType == "TextCard" && "header5" == item.data.type -> VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5
            item.type == "textCard" && item.data.dataType == "TextCard" && "footer2" == item.data.type -> VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2
            item.type == "followCard" && item.data.dataType == "FollowCard" -> VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW
            item.type == "informationCard" && item.data.dataType == "InformationCard" -> VIEW_HOLDER_TYPE_INFO_CARD_INFO
            item.type == "videoSmallCard" && item.data.dataType == "VideoBeanForClient" -> VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD
            item.type == "textCard" && item.data.dataType == "TextCardWithTagId" -> VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG
            item.type == "banner" && item.data.dataType == "Banner" -> VIEW_HOLDER_TYPE_BANNER
            item.type == "ugcSelectedCardCollection" && item.data.dataType == "ItemCollection" -> VIEW_HOLDER_TYPE_SELECTION_CARD
            item.type == "briefCard" && item.data.dataType == "TagBriefCard" -> VIEW_HOLDER_TYPE_BRIEF_CARD_TAG
            item.type == "briefCard" && item.data.dataType == "TopicBriefCard" -> VIEW_HOLDER_TYPE_BRIEF_CARD_TOP
            else -> VIEW_HOLDER_TYPE_NOTHING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutId = when (viewType) {
            VIEW_HOLDER_TYPE_TEXT_CARD_TITLE -> R.layout.home_rv_item_textcard_rightandleft
            VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5 -> R.layout.home_rv_item_textcard_textcard_header5
            VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2 -> R.layout.home_rv_item_textcard_textcard_footer2
            VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW -> R.layout.home_rv_item_followcard_followcard
            VIEW_HOLDER_TYPE_INFO_CARD_INFO -> R.layout.home_rv_item_infocard_infocard
            VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD -> R.layout.home_rv_item_video_small_card
            VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG -> R.layout.home_rv_item_textcard_with_tag_id
            VIEW_HOLDER_TYPE_BANNER -> R.layout.home_rv_item_banner
            VIEW_HOLDER_TYPE_SELECTION_CARD -> R.layout.home_rv_item_ugs_selectioncard
            VIEW_HOLDER_TYPE_BRIEF_CARD_TAG -> R.layout.home_rv_item_briefcard_tag
            VIEW_HOLDER_TYPE_BRIEF_CARD_TOP -> R.layout.home_rv_item_briefcard_top
            else -> R.layout.home_rv_item_textcard_rightandleft
        }
        return ItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHolder -> holder.bind(items[position])
        }
    }

    companion object {
        private const val VIEW_HOLDER_TYPE_TEXT_CARD_TITLE = 0
        private const val VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5 = 1
        private const val VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW = 2
        private const val VIEW_HOLDER_TYPE_INFO_CARD_INFO = 3
        private const val VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD = 4
        private const val VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG = 5
        private const val VIEW_HOLDER_TYPE_BANNER = 6
        private const val VIEW_HOLDER_TYPE_SELECTION_CARD = 7
        private const val VIEW_HOLDER_TYPE_BRIEF_CARD_TAG = 8
        private const val VIEW_HOLDER_TYPE_BRIEF_CARD_TOP = 9
        private const val VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2 = 10

        //没有匹配到
        private const val VIEW_HOLDER_TYPE_NOTHING = 100
    }

    class ItemHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            when (binding) {
                is HomeRvItemTextcardRightandleftBinding -> {
                    binding.apply {
                        header = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardTextcardHeader5Binding -> {
                    binding.apply {
                        header = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardTextcardFooter2Binding -> {
                    binding.apply {
                        header = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemFollowcardFollowcardBinding -> {
                    binding.apply {
                        daily = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemInfocardInfocardBinding -> {
                    binding.apply {
                        logd("info card...")
                        //绑定rv adapter
                        if (rvBanner.adapter == null) {
                            rvBanner.run {
                                //设置分割线
                                addItemDecoration(MarginDecoration(bottom = 20))
                                //布局方式
                                layoutManager = LinearLayoutManager(this.context)
                                //优化绘制
                                setHasFixedSize(true)
                                //设置adapter
                                adapter = InfoCardRvAdapter()
                            }
                        }
                        //更新数据
                        (rvBanner.adapter as InfoCardRvAdapter).submitList(item.data.titleList)
                        info = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemVideoSmallCardBinding -> {
                    binding.apply {
                        video = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardWithTagIdBinding -> {
                    binding.apply {
                        titleAction = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemBannerBinding -> {
                    binding.apply {
                        banner = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemUgsSelectioncardBinding -> {
                    binding.apply {
                        collection = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemBriefcardTagBinding -> {
                    binding.apply {
                        brief = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemBriefcardTopBinding -> {
                    binding.apply {
                        topic = item
                        executePendingBindings()
                    }
                }
            }

        }
    }


}