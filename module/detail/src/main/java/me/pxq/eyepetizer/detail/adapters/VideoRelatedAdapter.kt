package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.R
import me.pxq.common.data.Item
import me.pxq.common.databinding.RvItemVideoSmallCardBinding
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.utils.logd

/**
 * Description: 视频详情页-相关视频 adapter
 * Author : pxq
 * Date : 2020/8/9 8:16 PM
 */
class VideoRelatedAdapter(
    private val actionMV: BaseViewModel,
    val relatedVideos: MutableList<Item> = mutableListOf()
) :
    RecyclerView.Adapter<VideoRelatedAdapter.VideoRelatedHolder>() {

    inner class VideoRelatedHolder(private val binding: RvItemVideoSmallCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // 修改字体颜色
            binding.tvVideoCategory.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white25
                )
            )
            binding.tvVideoTitle.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.video_detail_video_title
                )
            )
        }

        fun bind(item: Item) {
            binding.viewModel = actionMV
            binding.video = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRelatedHolder {
        return VideoRelatedHolder(
            RvItemVideoSmallCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = relatedVideos.size

    override fun onBindViewHolder(holder: VideoRelatedHolder, position: Int) {
        holder.bind(relatedVideos[position])
    }
}