package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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