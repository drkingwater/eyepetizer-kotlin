package me.pxq.utils.ui.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.pxq.utils.logd

/**
 * Description: todo 水平滑动Banner间隔
 * Author : pxq
 * Date : 2020/8/16 1:27 PM
 */
class BannerDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    private val leftMarginValue = margin
    private val middleMargin = margin / 2

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount ?: 0
        view.also {
            logd(it)
        }
        when(position){
            0 -> outRect.set(middleMargin, 0, middleMargin, 0)
            count - 1 -> outRect.set(0, 0, middleMargin, 0)
            else -> outRect.set(-leftMarginValue, 0,0 -0, 0)
        }
    }

//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDrawOver(c, parent, state)
//        val count = parent.adapter?.itemCount ?: 0
//        for (i in 0 .. count){
//
//        }
//    }
}