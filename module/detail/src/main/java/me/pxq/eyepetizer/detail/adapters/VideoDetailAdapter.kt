package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.eyepetizer.detail.databinding.DetailRvItemVideoDetailHeaderBinding

/**
 * Description: adapter for [me.pxq.eyepetizer.detail.VideoDetailActivity] rv
 * Author : pxq
 * Date : 2020/8/8 8:15 PM
 */
class VideoDetailAdapter(val items: MutableList<Item> = mutableListOf()) :
    RecyclerView.Adapter<VideoDetailAdapter.VideoDetailHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when (item.type) {
            "video" -> 1
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoDetailHolder {
        return VideoDetailHolder(
            DetailRvItemVideoDetailHeaderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VideoDetailHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class VideoDetailHolder(private val binding: DetailRvItemVideoDetailHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            with(binding) {
                video = item
                executePendingBindings()
            }
        }
    }
}