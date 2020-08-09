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
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd
import me.pxq.utils.ui.decoration.MarginDecoration

/**
 * Description: adapter for [me.pxq.eyepetizer.detail.VideoDetailFragment] rv
 * Author : pxq
 * Date : 2020/8/8 8:15 PM
 */
class VideoDetailAdapter(
    private val actionMV: BaseViewModel,
    var count: Int = 0,
    var videoDetail: Item? = null,
    var relatedVideos: MutableList<Item> = mutableListOf()
) :
    RecyclerView.Adapter<VideoDetailAdapter.VideoDetailHolder>() {

    private val holderTypes = listOf("video", "videoSmallCard")

    override fun getItemViewType(position: Int): Int {
//        val item = items[position]
//        return when {
//            item.type == "video" && item.data.dataType == "VideoBeanForClient" -> 1
//            item.type == "videoSmallCard" && item.data.dataType == "VideoBeanForClient" -> 2
//            else -> 1
//        }

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
                            adapter = VideoRelatedAdapter(actionMV)
                            // Item分割区域
                            addItemDecoration(MarginDecoration(bottom = context.resources.getDimension(R.dimen.rv_divider_bottom).dp2px.toInt()))

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
