package  me.pxq.utils.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Description: 上下左右边距Decoration
 * Author : pxq
 * Date : 2020/7/31 8:02 PM
 */
class MarginDecoration(val left : Int= 0, val top : Int = 0, val right : Int = 0, val bottom : Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.set(left, top, right, bottom)
        } else {
            outRect.set(left, 0, right, bottom)
        }
    }

}