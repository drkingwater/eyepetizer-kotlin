package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.eyepetizer.home.databinding.*

/**
 * Description: [me.pxq.eyepetizer.home.ui.IndexFragment] 推荐栏 Rv Adapter
 * Author : pxq
 * Date : 2020/7/29 9:57 PM
 */
class IndexRvAdapter(var items: List<Item> = emptyList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when {
            item.type == "textCard" && item.data.dataType == "TextCardWithRightAndLeftTitle" -> 0
            item.type == "textCard" && item.data.dataType == "TextCard" -> 1
            item.type == "followCard" && item.data.dataType == "FollowCard" -> 2
            else -> 100
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ItemHolder(
                HomeRvItemTextcardRightandleftBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            1 -> ItemHolder(
                HomeRvItemTextcardTextcardBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            2 -> ItemHolder(
                HomeRvItemFollowcardFollowcardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ItemHolder(
                HomeRvItemTextcardRightandleftBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHolder -> holder.bind(items[position])
        }
    }

    class ItemHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            when (binding) {
                is HomeRvItemTextcardRightandleftBinding -> {
                    binding.apply {
                        header = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardTextcardBinding -> {
                    binding.apply {
                        header = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemFollowcardFollowcardBinding -> {
                    binding.apply {
                        daily = item
                        executePendingBindings()
                    }
                }
            }

        }
    }
}