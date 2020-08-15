package me.pxq.eyepetizer.community.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Banner
import me.pxq.common.data.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.databinding.CommunityRvItemHorScrollCardItemBinding

/**
 * Description: adapter for type : "horizontalScrollCard - squareCardOfCommunityContent"
 * Author : pxq
 * Date : 2020/8/15 4:24 PM
 */
class HorizontalScrollCardAdapter(
    private val actionMV: BaseViewModel
) :
    ListAdapter<Item, HorizontalScrollCardAdapter.HorizontalScrollCardHolder>(TitleDiffCallBack()) {

    inner class HorizontalScrollCardHolder(private val binding: CommunityRvItemHorScrollCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            with(binding) {
                content = item
                viewModel = actionMV
                executePendingBindings()
            }
        }
    }

    internal class TitleDiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data.title == newItem.data.title && oldItem.data.subTitle == newItem.data.subTitle
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalScrollCardHolder {
        return HorizontalScrollCardHolder(
            CommunityRvItemHorScrollCardItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HorizontalScrollCardHolder, position: Int) {
        holder.bind(getItem(position))
    }
}