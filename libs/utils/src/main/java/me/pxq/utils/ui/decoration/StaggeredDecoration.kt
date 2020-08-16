package  me.pxq.utils.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import me.pxq.utils.logd

/**
 * Description: 瀑布流左右边距Decoration
 * Author : pxq
 * Date : 2020/7/31 8:02 PM
 */
class StaggeredDecoration(val left: Int = 0, val right: Int = 0, var margin : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutParams = view.layoutParams
        val position = parent.getChildAdapterPosition(view)
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val index = layoutParams.spanIndex
            logd("index $index")
            when {
                position > 1 && index % 2 == 0 -> {  // 左边的
                    outRect.set(left, 0, margin, 0)
                }
                position > 1 && index % 2 == 1 -> {
                    outRect.set(0, 0, right, 0)
                }
            }
        }
    }

}