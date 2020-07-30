package me.pxq.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import me.pxq.common.R

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/30 9:03 PM
 */
class TypefaceTextView(context: Context, attributeSet: AttributeSet? = null) :
    AppCompatTextView(context, attributeSet) {

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.TypefaceTextView).run {
            if (hasValue(R.styleable.TypefaceTextView_typeface)) {
                getString(R.styleable.TypefaceTextView_typeface)?.let {
                    //从缓存中拿字体信息
                    this@TypefaceTextView.typeface = FontCache.getTypeface(context, it)
                }

            }

            recycle()
        }
    }

}