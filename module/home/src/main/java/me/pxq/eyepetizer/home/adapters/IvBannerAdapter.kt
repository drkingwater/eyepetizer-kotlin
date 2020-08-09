package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.databinding.HomeRvItemBannerBinding
import me.pxq.common.viewmodel.BaseViewModel
import kotlin.math.acos

/**
 * Description: 水平滚动图片rv adapter
 * Author : pxq
 * Date : 2020/8/3 8:28 PM
 */
class IvBannerAdapter(val actionMV : BaseViewModel) : ListAdapter<Item, IvBannerAdapter.IvBannerHolder>(TitleDiffCallBack()) {


    /**
     *  Image Banner holder
     */
    inner class IvBannerHolder(private val binding: HomeRvItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.apply {
                banner = item
                viewModel = actionMV
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IvBannerHolder {
        return IvBannerHolder(HomeRvItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IvBannerHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class TitleDiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data.title == newItem.data.title
        }

    }
}
