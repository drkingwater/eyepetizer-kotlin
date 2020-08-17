package me.pxq.common.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import me.pxq.utils.logi

/**
 * Description: 判断滑动到底部rv
 * Author : pxq
 * Date : 2020/8/13 9:59 PM
 */
class BottomRecyclerView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {


    fun setOnBottomListener(action: () -> Unit) {
        addOnScrollListener(object : OnScrollListener() {

            private var onBottom = false

            private var lastVisibleItemPosition = 0

            //当前屏幕所看到的子项个数
            private var visibleItemCount = 0

            //当前 RecyclerView 的所有子项个数
            private var totalItemCount = 0

            private var dy = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_IDLE && visibleItemCount > 0 && lastVisibleItemPosition >= totalItemCount - 5 && dy > 0) {
                    logi("BottomRecyclerView 到底了,刷新数据...")
                    action()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                this.dy = dy
                //当前屏幕所看到的子项个数
                visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
                //当前 RecyclerView 的所有子项个数
                totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
                when (val layoutManager = recyclerView.layoutManager) {
                    is LinearLayoutManager -> {
                        //屏幕中最后一个可见子项的 position
                        lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    }
                    is StaggeredGridLayoutManager -> {
                        lastVisibleItemPosition =
                            layoutManager.findLastVisibleItemPositions(IntArray(layoutManager.spanCount))
                                .max() ?: 0
                    }
                }
            }
        })
    }

}