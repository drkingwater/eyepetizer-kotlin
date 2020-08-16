package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.R
import me.pxq.common.binding.bindDuration
import me.pxq.common.data.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.detail.databinding.DetailRvItemRepliesBinding
import me.pxq.eyepetizer.detail.databinding.DetailRvItemVideoDetailHeaderBinding
import me.pxq.eyepetizer.detail.databinding.DetailRvItemVideoRelatedBinding
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModel
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.ui.decoration.MarginDecoration

/**
 * Description: adapter for [me.pxq.eyepetizer.detail.VideoDetailFragment] rv
 * Author : pxq
 * Date : 2020/8/8 8:15 PM
 */
class VideoDetailAdapter(
    private val actionMV: VideoDetailViewModel,
    var count: Int = 0,
    var videoDetail: Item? = null,
    var relatedVideos: MutableList<Item> = mutableListOf(),
    var replies: MutableList<Item> = mutableListOf()
) :
    RecyclerView.Adapter<VideoDetailAdapter.VideoDetailHolder>() {

    // viewTypes
    private val holderTypes = listOf("video", "videoSmallCard", "replay")

    // 相关推荐视频adapter
    private lateinit var videoRelatedAdapter: VideoRelatedAdapter

    // 评论adapter
    private lateinit var replayAdapter: VideoReplayAdapter

    // 相关推荐视频binding
    private var relatedVideosBinding: DetailRvItemVideoRelatedBinding? = null

    // 设置"查看更多"是否可见
    fun setLoadMoreRelatedVisible(visible: Boolean) {
        relatedVideosBinding?.tvSeeMore?.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    // 查看更多视频
    fun onMoreRelatedVideosLoaded(videos: List<Item>) {
        with(videoRelatedAdapter) {
            val start = relatedVideos.size
            relatedVideos.addAll(videos)
            notifyItemRangeInserted(start, relatedVideos.size)
        }
    }

    // 加载更多评论
    fun onMoreRepliesLoaded(_replies: List<Item>) {
        with(replayAdapter) {
            val start = replies.size
            replies.addAll(_replies)
            notifyItemRangeInserted(start, replies.size)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (holderTypes[position]) {
            "video" -> 1
            "videoSmallCard" -> 2
            "replay" -> 3
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoDetailHolder {
        return when (viewType) {
            1 -> {    // 视频信息
                VideoDetailHolder(
                    DetailRvItemVideoDetailHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            2 -> {   // 推荐视频
                VideoDetailHolder(
                    DetailRvItemVideoRelatedBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ).also {
                        relatedVideosBinding = it
                    }
                )
            }
            3 -> {   // 评论
                VideoDetailHolder(
                    DetailRvItemRepliesBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> VideoDetailHolder(
                DetailRvItemVideoDetailHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun getItemCount() = count

    override fun onBindViewHolder(holder: VideoDetailHolder, position: Int) {
        when (position) {
            0 -> videoDetail?.run {
                holder.bind(this)
            }
            1 -> holder.bind(relatedVideos)
            2 -> holder.bind(replies)
            else -> holder.itemView.visibility = View.GONE
        }
    }

    inner class VideoDetailHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            if (binding is DetailRvItemVideoDetailHeaderBinding) {
                binding.video = item
                binding.executePendingBindings()
            }
        }

        fun bind(items: List<Item>) {
            when (binding) {
                is DetailRvItemVideoRelatedBinding -> {   // 推荐视频
                    with(binding.rvVideoRelated) {
                        if (adapter == null) {
                            // 初始化
                            binding.detailViewModel = actionMV

                            adapter = VideoRelatedAdapter(actionMV).also {
                                videoRelatedAdapter = it
                            }
                            // Item分割区域
                            addItemDecoration(
                                MarginDecoration(
                                    bottom = context.resources.getDimension(
                                        R.dimen.rv_divider_bottom
                                    ).toInt()
                                )
                            )

                            layoutManager =
                                LinearLayoutManager(
                                    context,
                                    RecyclerView.VERTICAL,
                                    false
                                )
                        }
                        // 刷新数据
                        (adapter as VideoRelatedAdapter).run {
                            relatedVideos.clear()
                            relatedVideos.addAll(items)
                            notifyDataSetChanged()
                            logd("video related notify ${relatedVideos.size}")
                        }
                    }

                    binding.executePendingBindings()
                }
                is DetailRvItemRepliesBinding -> {   // 评论
                    with(binding.rvReplies) {
                        if (adapter == null) {
                            // 设置adapter
                            adapter = VideoReplayAdapter().also {
                                replayAdapter = it
                            }
                            // Item分割区域
                            addItemDecoration(
                                MarginDecoration(
                                    bottom = context.resources.getDimension(
                                        R.dimen.rv_divider_bottom
                                    ).toInt()
                                )
                            )
                            layoutManager =
                                LinearLayoutManager(
                                    context,
                                    RecyclerView.VERTICAL,
                                    false
                                )
//                            setOnBottomListener {   // 加载更多视频
//                                actionMV.fetchMoreVideoReplies()
//                            }
                        }
                        (adapter as VideoReplayAdapter).run {
                            replies.clear()
                            replies.addAll(items)
                            notifyDataSetChanged()
                        }
                    }
                    binding.executePendingBindings()
                }

            }
        }
    }
}
