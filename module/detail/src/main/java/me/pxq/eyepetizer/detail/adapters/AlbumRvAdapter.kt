package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.pxq.eyepetizer.detail.databinding.DetailAlbumRvItemBinding
import me.pxq.utils.extensions.load

/**
 * Description: 图片rv adapter
 * Author : pxq
 * Date : 2020/8/26 11:47 PM
 */
class AlbumRvAdapter(private val urls: List<String>) :
    RecyclerView.Adapter<AlbumRvAdapter.AlbumRvHolder>() {

    class AlbumRvHolder(private val binding: DetailAlbumRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.ivAlbum.load(url)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumRvHolder {
        return AlbumRvHolder(
            DetailAlbumRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumRvHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount() = urls.size
}