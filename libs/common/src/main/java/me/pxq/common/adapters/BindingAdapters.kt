package me.pxq.common.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import me.pxq.common.data.Item

/**
 * Description: databingding修改TextView字体
 * Author : pxq
 * Date : 2020/7/29 11:24 PM
 */
@BindingAdapter("icon")
fun bindIcon(imageView: ImageView, url: String?) {
    url ?: return
    Glide.with(imageView)
        .load(url)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(imageView)
}

@BindingAdapter("cover")
fun bindCover(imageView: ImageView, url: String?) {
    url ?: return
    Glide.with(imageView)
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
        .into(imageView)
}


