package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.framework.model.Item
import me.pxq.eyepetizer.home.databinding.HomeRvItemSpecialSquareGridItemBinding
import me.pxq.framework.viewmodel.BaseViewModel

/**
 * Description: 热门分类 rv adapter
 * Author : pxq
 * Date : 2020/8/3 11:15 PM
 */
class SpecialSquareAdapter(val actionMV: BaseViewModel) :
    ListAdapter<Item, SpecialSquareAdapter.SpecialSquareHolder>(CategoryDiffCallBack()) {

    inner class SpecialSquareHolder(private val binding: HomeRvItemSpecialSquareGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                category = item
                viewModel = actionMV
                executePendingBindings()
            }
        }
    }


    internal class CategoryDiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data.title == newItem.data.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialSquareHolder {
        return SpecialSquareHolder(
            HomeRvItemSpecialSquareGridItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecialSquareHolder, position: Int) {
        holder.bind(getItem(position))
    }
}