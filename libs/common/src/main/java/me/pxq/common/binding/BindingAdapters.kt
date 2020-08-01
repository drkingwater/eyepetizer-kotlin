package me.pxq.common.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import me.pxq.common.R
import me.pxq.common.data.Header

/**
 * Description: DataBinding Adapters
 * Author : pxq
 * Date : 2020/7/29 11:24 PM
 */
@BindingAdapter("iconUrl", "iconType")
fun bindIcon(imageView: ImageView, url: String?, iconType: String?) {
    url ?: return
    Glide.with(imageView)
        .load(url)
        .apply(
            when (iconType) { //根据icon类型裁剪
                Header.ICON_TYPE_ROUND -> RequestOptions.bitmapTransform(CircleCrop())
                else -> RequestOptions.bitmapTransform(RoundedCorners(20))
            }
        )
        .into(imageView)

}

/**
 * 判断当前item是否是作者,根据[iconType]icon类型判断，如果是作者则显示图标，否则隐藏
 */
@BindingAdapter("isAuthor")
fun bindIsAuthor(imageView: ImageView, iconType: String?) {
    iconType ?: return
    imageView.visibility = if (Header.ICON_TYPE_ROUND == iconType) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * FollowCard 视频cover，圆角
 */
@BindingAdapter("cover")
fun bindCover(imageView: ImageView, url: String?) {
    url ?: return
    Glide.with(imageView)
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
        .into(imageView)
}

/**
 * FollowCard 时间格式化
 */
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


@BindingAdapter("infoUrl")
fun bindInfoBg(imageView: ImageView, url: String?){
    url ?: return
    Glide.with(imageView)
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
        .into(imageView)
}


