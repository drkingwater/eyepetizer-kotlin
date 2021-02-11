package me.pxq.eyepetizer.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import me.pxq.framework.model.Item
import me.pxq.eyepetizer.detail.databinding.DetailAlbumVpItemBinding

/**
 * Description: album vp adapter
 * Author : pxq
 * Date : 2020/8/26 11:36 PM
 */
class AlbumVpAdapter(val albums: MutableList<Item>) :
    RecyclerView.Adapter<AlbumVpAdapter.AlbumVpHolder>() {


    class AlbumVpHolder(private val binding: DetailAlbumVpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Item) {
            with(binding.viewpager) {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                adapter = AlbumRvAdapter(
                    album.data.content.data.urls ?: listOf(album.data.content.data.cover.detail)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumVpHolder {
        return AlbumVpHolder(
            DetailAlbumVpItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumVpHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount() = albums.size

}