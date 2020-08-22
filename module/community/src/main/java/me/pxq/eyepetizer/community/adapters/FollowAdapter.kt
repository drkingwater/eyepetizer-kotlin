package me.pxq.eyepetizer.community.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.data.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.R
import me.pxq.eyepetizer.community.databinding.CommunityRvItemAutoPlayFollowCardBinding

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/22 5:02 PM
 */
class FollowAdapter(
    private val actionVM: BaseViewModel,
    val items: MutableList<Item> = mutableListOf()
) : RecyclerView.Adapter<FollowAdapter.FollowHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowHolder {
        return FollowHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.community_rv_item_auto_play_follow_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class FollowHolder(private val binding: CommunityRvItemAutoPlayFollowCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            with(binding) {
                viewModel = actionVM
                follow = item
                executePendingBindings()
            }
        }

    }

}