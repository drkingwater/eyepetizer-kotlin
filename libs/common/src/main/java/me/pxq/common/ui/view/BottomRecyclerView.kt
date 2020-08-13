package me.pxq.common.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

            private var layoutManager: LinearLayoutManager? = null

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_IDLE && onBottom) {
                    logi("BottomRecyclerView 到底了,刷新数据...")
                    action()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager == null) {
                    layoutManager = recyclerView.layoutManager as LinearLayoutManager
                }
                with(layoutManager!!) {
                    //屏幕中最后一个可见子项的 position
                    val lastVisibleItemPosition = findLastVisibleItemPosition()
                    //当前屏幕所看到的子项个数
                    val visibleItemCount = childCount
                    //当前 RecyclerView 的所有子项个数
                    val totalItemCount = itemCount
                    //RecyclerView 的滑动状态
                    onBottom = visibleItemCount > 0 && lastVisibleItemPosition >= totalItemCount - 5  // 提前加载
                }

            }
        })
    }

}