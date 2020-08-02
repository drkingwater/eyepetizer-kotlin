package me.pxq.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.RequestOptions
import me.pxq.utils.glide.RoundedCornersTransformation

/**
 * Description: kotlin扩展方法
 * Author : pxq
 * Date : 2020/8/2 6:10 PM
 */

/**
 * 根据手机的分辨率将dp转成为px。
 */
val Float.dp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * 图片加载扩展方法
 */
fun ImageView.load(url: String, trans: Transformation<Bitmap>? = null) {
    if (trans == null){
        Glide.with(this)
            .load(url)
            .into(this)
    } else {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions.bitmapTransform(trans)
            )
            .into(this)
    }

}