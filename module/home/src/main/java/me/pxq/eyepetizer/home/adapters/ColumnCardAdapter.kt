package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.eyepetizer.home.databinding.HomeRvItemColumnCardGridItemBinding
import me.pxq.eyepetizer.home.viewmodel.BaseViewModel
import me.pxq.utils.logd

/**
 * Description: 首页-发现 专题策划 rv grid adapter
 * Author : pxq
 * Date : 2020/8/3 11:55 PM
 */
class ColumnCardAdapter(val actionVM : BaseViewModel) :
    ListAdapter<Item, ColumnCardAdapter.ColumnCardHolder>(CategoryDiffCallBack()) {

    inner class ColumnCardHolder(private val binding: HomeRvItemColumnCardGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                column = item
                viewModel = actionVM
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColumnCardHolder {
        return ColumnCardHolder(
            HomeRvItemColumnCardGridItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ColumnCardHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class CategoryDiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data.title == newItem.data.title
        }

    }
}