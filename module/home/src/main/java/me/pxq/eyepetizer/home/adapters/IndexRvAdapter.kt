package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.eyepetizer.home.databinding.HomeRecyclerItemTextcardHeader5Binding
import me.pxq.eyepetizer.home.databinding.HomeRecyclerItemTextcardHeader7Binding

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/29 9:57 PM
 */
class IndexRvAdapter(var items: List<Item> = emptyList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when {
            item.type == "textCard" && item.data.type == "header7" -> 0
            item.type == "textCard" && item.data.type == "header5" -> 1
            else -> 100
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TextCardH7(
                HomeRecyclerItemTextcardHeader7Binding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            1 -> TextCardH7(
                HomeRecyclerItemTextcardHeader5Binding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> TextCardH7(
                HomeRecyclerItemTextcardHeader7Binding.inflate(
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
            is TextCardH7 -> holder.bind(items[position])
        }
    }

    class TextCardH7(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Item) {
            when (binding) {
                is HomeRecyclerItemTextcardHeader5Binding -> {
                    binding.apply {
                        header = items
                        executePendingBindings()
                    }
                }
                is HomeRecyclerItemTextcardHeader7Binding -> {
                    binding.apply {
                        header = items
                        executePendingBindings()
                    }
                }
            }

        }
    }
}