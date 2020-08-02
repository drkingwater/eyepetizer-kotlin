package me.pxq.common.binding

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import me.pxq.common.R
import me.pxq.common.data.Follow
import me.pxq.common.data.Header
import me.pxq.common.data.Item
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.glide.RoundedCornersTransformation

/**
 * Description: DataBinding Adapters
 * Author : pxq
 * Date : 2020/7/29 11:24 PM
 */

/**
 * 控制view显示与否
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

/**
 * 根据[iconType]加载图片
 */
@BindingAdapter("iconUrl", "iconType", requireAll = false)
fun bindIcon(imageView: ImageView, url: String?, iconType: String? = Header.ICON_TYPE_ROUND) {
    url ?: return
    Glide.with(imageView)
        .load(url)
        .apply(
            when (iconType) { //根据icon类型裁剪
                Header.ICON_TYPE_ROUND -> RequestOptions.bitmapTransform(CircleCrop())
                else -> RequestOptions.bitmapTransform(RoundedCorners(defaultRoundRadius().toInt()))
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
 * TextCard header5 判断是否关注
 */
@BindingAdapter("followed")
fun bindFollowed(textView: TextView, follow: Follow?) {
    follow?.run {
        textView.visibility = if (followed) {
            View.GONE
        } else {
            View.VISIBLE
        }
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
        .apply(RequestOptions.bitmapTransform(RoundedCorners(defaultRoundRadius().toInt())))
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


/**
 * 开眼咨询，图片要求：顶部圆角，底部不变
 */
@BindingAdapter("infoUrl")
fun bindInfoBg(imageView: ImageView, url: String?) {
    url ?: return
    Glide.with(imageView)
        .load(url)
//        .apply(RequestOptions.bitmapTransform(SingleRoundedCorners(20, TransUtils.TYPE_TOP)))
        .apply(
            RequestOptions.bitmapTransform(
                RoundedCornersTransformation(
                    defaultRoundRadius().toInt(),
                    0,
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
        )
        .into(imageView)
}

//selected card
/**
 * Rv Item SelectedCard图片加载，[selections]：图片源,[index]:要选择的图片索引
 */
@BindingAdapter("selections", "select")
fun bindSelectedCard(imageView: ImageView, selections: List<Item>?, index: Int) {
    selections?.run {
        //图片不够
        if (size <= index) {
            imageView.visibility = View.GONE
        } else {
            val type = when (index) {
                0 -> RoundedCornersTransformation.CornerType.LEFT
                1 -> RoundedCornersTransformation.CornerType.TOP_RIGHT
                else -> RoundedCornersTransformation.CornerType.BOTTOM_RIGHT
            }
            Glide.with(imageView)
                .load(this[index].data.url)
//                .apply(RequestOptions.bitmapTransform(SingleRoundedCorners(20, type)))
                .apply(
                    RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(
                            defaultRoundRadius().toInt(), 0, type
                        )
                    )
                )
                .into(imageView)
        }
    }

}

//获取默认的圆角尺寸
fun defaultRoundRadius() = 5f.dp2px


