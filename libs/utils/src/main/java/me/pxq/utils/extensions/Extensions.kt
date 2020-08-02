package me.pxq.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

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