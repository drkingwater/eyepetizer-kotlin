package me.pxq.framework.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import me.pxq.framework.R

/**
 * Description: ImageView支持添加前景色
 * Author : pxq
 * Date : 2020/8/11 9:28 PM
 */
class ForegroundImageView(context: Context, attributeSet: AttributeSet? = null) : AppCompatImageView(context, attributeSet) {

    private var foreColor : Int? = null

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.ForegroundImageView).run {
            if (hasValue(R.styleable.ForegroundImageView_foreColor)) {
                foreColor = getColor(R.styleable.ForegroundImageView_foreColor, 0)
            }
            recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (foreColor != null && foreColor != 0){
            canvas.drawColor(foreColor!!)
        }
    }

}