package me.pxq.common.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import me.pxq.common.R

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

@BindingAdapter("duration")
fun bindDuration(textView: TextView, duration: Int) {
    //计算时间
    val hours = duration / 3600
    val minutes = (duration - hours * 3600) / 60
    val seconds = duration - (hours * 3600 + minutes * 60)
    //格式化时间
    val format = when {
        hours > 0 -> textView.context.getString(R.string.duration_format_hms)
            .format(hours, minutes, seconds)
        else -> textView.context.getString(R.string.duration_format_ms)
            .format(minutes, seconds)
    }
    textView.text = format
}


