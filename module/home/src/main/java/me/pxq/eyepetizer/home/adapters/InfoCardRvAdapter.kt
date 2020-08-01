package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.eyepetizer.home.R

/**
 * Description: Info Card titleList Rv Adapter
 * Author : pxq
 * Date : 2020/8/1 11:36 AM
 */
class InfoCardRvAdapter() : ListAdapter<String, InfoCardRvAdapter.BannerHolder>(TitleDiffCallBack()) {


    /**
     * Title TV holder
     */
    class BannerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle : TextView = view.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        return BannerHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_info_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.tvTitle.text = getItem(position)
    }

    internal class TitleDiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}
