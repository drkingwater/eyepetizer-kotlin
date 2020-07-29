package me.pxq.common.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Description: databingding修改TextView字体
 * Author : pxq
 * Date : 2020/7/29 11:24 PM
 */
@BindingAdapter("typeface")
fun setTvTypeface(textView: View, typeface: String) {
    try {
        if (textView is TextView) {
            val type = getTypeface(textView.context, typeface)
            textView.typeface = type
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getTypeface(context: Context, typeface: String) = when (typeface) {
    "FZLanL" -> {
        Typeface.createFromAsset(
            context.assets,
            "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
        )
    }
    "FZLanD" -> {
        Typeface.createFromAsset(
            context.assets,
            "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
        )
    }
    "Futura" -> {
        Typeface.createFromAsset(
            context.assets,
            "fonts/Futura-CondensedMedium.ttf"
        )
    }
    "DIN" -> {
        Typeface.createFromAsset(
            context.assets,
            "fonts/DIN-Condensed-Bold.ttf"
        )
    }
    "Lobster" -> {
        Typeface.createFromAsset(
            context.assets,
            "fonts/Lobster-1.4.otf"
        )
    }
    else -> null
}
