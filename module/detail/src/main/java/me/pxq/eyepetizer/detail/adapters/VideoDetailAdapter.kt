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
    var relatedVideos: MutableList<Item> = mutableListOf()
) :
    RecyclerView.Adapter<VideoDetailAdapter.VideoDetailHolder>() {

    // viewTypes
    private val holderTypes = listOf("video", "videoSmallCard")

    // 相关推荐视频adapter
    private lateinit var videoRelatedAdapter: VideoRelatedAdapter

    // 相关推荐视频binding
    private var relatedVideosBinding : DetailRvItemVideoRelatedBinding? = null

    // 设置"查看更多"是否可见
    fun setLoadMoreRelatedVisible(visible : Boolean){
        relatedVideosBinding?.tvSeeMore?.visibility = if (visible){
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    // 查看更多视频
    fun loadMoreRelatedVideos(videos : List<Item>){
        with(videoRelatedAdapter) {
            val start = relatedVideos.size
            relatedVideos.addAll(videos)
            notifyItemRangeInserted(start, relatedVideos.size)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (holderTypes[position]) {
            "video" -> 1
            "videoSmallCard" -> 2
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoDetailHolder {
        return when (viewType) {
            1 -> {
                VideoDetailHolder(
                    DetailRvItemVideoDetailHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            2 -> {
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
                is DetailRvItemVideoRelatedBinding -> {
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
                                    ).dp2px.toInt()
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
            }
        }
    }
}
