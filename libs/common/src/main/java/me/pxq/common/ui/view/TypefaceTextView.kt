package me.pxq.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import me.pxq.common.R

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/30 9:03 PM
 */
class TypefaceTextView(context: Context, attributeSet: AttributeSet? = null) : AppCompatTextView(context, attributeSet) {

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.TypefaceTextView).run {
            if (hasValue(R.styleable.TypefaceTextView_typeface)){
                val typeface = getString(R.styleable.TypefaceTextView_typeface)
                this@TypefaceTextView.typeface = getTypeface(context, typeface)
            }

            recycle()
        }
    }

    private fun getTypeface(context: Context, typeface: String?) = when (typeface) {
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

}