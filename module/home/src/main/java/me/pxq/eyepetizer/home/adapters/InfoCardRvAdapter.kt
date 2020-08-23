package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.model.Banner
import me.pxq.eyepetizer.home.R
import me.pxq.common.viewmodel.BaseViewModel

/**
 * Description: Info Card titleList Rv Adapter
 * Author : pxq
 * Date : 2020/8/1 11:36 AM
 */
class InfoCardRvAdapter(val actionVM: BaseViewModel) :
    ListAdapter<Banner, InfoCardRvAdapter.BannerHolder>(TitleDiffCallBack()) {


    /**
     * Title TV holder
     */
    inner class BannerHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitle: TextView = view.findViewById(R.id.tv_title)

        lateinit var actionUrl : String

        init {
            tvTitle.setOnClickListener {
                actionVM.execAction(it, actionUrl)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        return BannerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_info_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.tvTitle.text = getItem(position).also {
            holder.actionUrl = it.link
        }.run {
            "$tag_name | $title"
        }

    }

    internal class TitleDiffCallBack : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.tag_name == newItem.tag_name && oldItem.title == oldItem.title
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

    }
}
