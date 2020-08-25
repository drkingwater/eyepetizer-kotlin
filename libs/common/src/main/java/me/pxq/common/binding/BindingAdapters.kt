package me.pxq.common.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import me.pxq.common.R
import me.pxq.common.model.Follow
import me.pxq.common.model.Header
import me.pxq.common.model.Item
import me.pxq.common.model.Tag
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.extensions.load
import me.pxq.utils.glide.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.*

/**
 * Description: DataBinding Adapters
 * Author : pxq
 * Date : 2020/7/29 11:24 PM
 */

internal val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
internal val dateFormatPublish = SimpleDateFormat("HH:mm", Locale.CHINA)

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
    imageView.load(
        url, when (iconType) { //根据icon类型裁剪
            Header.ICON_TYPE_ROUND -> CircleCrop()
            else -> RoundedCorners(defaultRoundRadius().toInt())
        }
    )

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
    if (follow == null) {
        textView.visibility = View.GONE
        return
    }
    follow.run {
        textView.visibility = if (followed) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}

/**
 * cover图片，圆角
 */
@BindingAdapter("cover")
fun bindCover(imageView: ImageView, url: String?) {
    url ?: return
    imageView.load(
        url,
        RoundedCorners(defaultRoundRadius().toInt()),
        placeHolderId = R.drawable.shape_bg_album_loading
    )
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
    imageView.load(
        url, RoundedCornersTransformation(
            defaultRoundRadius().toInt(),
            0,
            RoundedCornersTransformation.CornerType.TOP
        )
    )
}

//selected card

/**
 * Rv Item SelectedCard图片加载，
 * [selections]：图片源
 * [index]:要选择的图片索引
 * [isIcon]:是否是用户头像
 * [multiPicTag]:是否多图标志
 */
@BindingAdapter("selections", "select", "isIcon", "multiPicTag", requireAll = false)
fun bindSelectedCard(
    view: View,
    selections: List<Item>?,
    index: Int,
    isIcon: Boolean = false,
    multiPicTag: Boolean = false
) {
    selections?.run {
        //图片不够
        if (size <= index) {
            view.visibility = View.GONE
        } else {
            when (view) {
                is ImageView -> {
                    when {
                        //用户头像标志
                        isIcon -> view.load(this[index].data.userCover, CircleCrop())
                        //多图标志
                        multiPicTag -> {
                            view.visibility = this[index].data.urls?.run {
                                if (size <= 1) {
                                    View.GONE
                                } else {
                                    View.VISIBLE
                                }
                            } ?: View.GONE

                        }
                        //cover
                        else -> {
                            val type = when (index) {
                                0 -> RoundedCornersTransformation.CornerType.LEFT
                                1 -> RoundedCornersTransformation.CornerType.TOP_RIGHT
                                else -> RoundedCornersTransformation.CornerType.BOTTOM_RIGHT
                            }
                            view.load(
                                this[index].data.url, RoundedCornersTransformation(
                                    defaultRoundRadius().toInt(), 0, type
                                ),
                                placeHolderId = R.drawable.shape_bg_album_loading
                            )
                        }
                    }

                }
                is TextView -> { //用户名
                    view.text = this[index].data.nickname
                }
                else -> null
            }

        }
    }
}

@BindingAdapter("createDate")
fun bindReplayDate(textView: TextView, createTime: Long) {
    textView.text = simpleDateFormat.format(Date(createTime))
}

@BindingAdapter("isMultiPic")
fun bindIsMultiPic(imageView: ImageView, list: List<Any>?) {
    imageView.visibility = when {
        list.isNullOrEmpty() -> View.GONE
        list.size > 1 -> View.VISIBLE
        else -> View.GONE
    }
}

/**
 * 社区-关注 发布TextView时间
 */
@BindingAdapter("publishTime")
fun bindPublishTime(textView: TextView, time: Long) {
    textView.text = String.format(
        Locale.CHINA,
        textView.context.getString(R.string.publish_with_time),
        dateFormatPublish.format(Date(time))
    )
}

@BindingAdapter("albumTag")
fun bindTag(textView: TextView, tags: List<Tag>?) {
    if (tags.isNullOrEmpty()) {
        textView.visibility = View.GONE
        return
    }
    textView.text = tags[0].name

}


//获取默认的圆角尺寸
fun defaultRoundRadius() = 5f.dp2px


