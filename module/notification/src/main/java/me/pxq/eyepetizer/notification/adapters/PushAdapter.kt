package me.pxq.eyepetizer.notification.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.pxq.common.model.Message
import me.pxq.common.model.Messages
import me.pxq.eyepetizer.notification.databinding.NotificationRvItemPushBinding
import me.pxq.eyepetizer.notification.viewmodels.PushViewModel
import me.pxq.utils.logd

/**
 * Description: 推送RV adapter
 * Author : pxq
 * Date : 2020/8/23 5:49 PM
 */
class PushAdapter(
    private val actionVM: PushViewModel,
    val messages: MutableList<Message> = mutableListOf()
) :
    RecyclerView.Adapter<PushAdapter.PushHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PushHolder(
        NotificationRvItemPushBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PushHolder, position: Int) =
        holder.bind(messages[position])

    override fun getItemCount() = messages.size

    inner class PushHolder(private val binding: NotificationRvItemPushBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
            binding.viewModel = actionVM
            binding.executePendingBindings()
        }
    }
}