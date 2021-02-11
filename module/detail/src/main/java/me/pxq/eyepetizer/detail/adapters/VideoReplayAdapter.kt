package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import me.pxq.framework.model.Item
import me.pxq.eyepetizer.detail.databinding.DetailRvItemRepliesItemBinding
import me.pxq.eyepetizer.detail.databinding.DetailRvItemTextcardHeader4Binding

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/10 10:36 PM
 */
class VideoReplayAdapter(var replies: MutableList<Item> = mutableListOf()) :
    RecyclerView.Adapter<VideoReplayAdapter.VideoReplayHolder>() {

    override fun getItemViewType(position: Int): Int {
        val replay = replies[position]
        return when {
            replay.type == "textCard" && replay.data.dataType == "TextCard" -> 1  //header
            else -> 2    //评论
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoReplayHolder {
        return when (viewType) {
            1 -> VideoReplayHolder(
                DetailRvItemTextcardHeader4Binding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> VideoReplayHolder(
                DetailRvItemRepliesItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = replies.size

    override fun onBindViewHolder(holder: VideoReplayHolder, position: Int) {
        holder.bind(replies[position])
    }


    inner class VideoReplayHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            when (binding) {
                is DetailRvItemTextcardHeader4Binding -> {  //header
                    binding.header = item
                    binding.executePendingBindings()
                }
                is DetailRvItemRepliesItemBinding -> {    //评论
                    binding.replay = item
                    binding.executePendingBindings()
                }
            }
        }

    }
}