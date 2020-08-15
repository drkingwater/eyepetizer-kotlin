package me.pxq.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.common.databinding.RvItemBannerBinding
import me.pxq.common.viewmodel.BaseViewModel
import kotlin.math.acos

/**
 * Description: 水平滚动图片rv adapter
 * Author : pxq
 * Date : 2020/8/3 8:28 PM
 */
class IvBannerAdapter2(val actionMV : BaseViewModel, val items : MutableList<Item> = mutableListOf()) : RecyclerView.Adapter<IvBannerAdapter2.IvBannerHolder>() {


    /**
     *  Image Banner holder
     */
    inner class IvBannerHolder(private val binding: RvItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.apply {
                banner = item
                viewModel = actionMV
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IvBannerHolder {
        return IvBannerHolder(RvItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IvBannerHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


}
