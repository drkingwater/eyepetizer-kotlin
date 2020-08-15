package me.pxq.common.ui.view

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import me.pxq.utils.logd
import kotlin.math.abs

/**
 * Description: 处理vp2嵌套vp2水平滑动冲突，必须要包含vp2子view
 * Author : pxq
 * Date : 2020/8/15 11:53 PM
 */
class Vp2BannerLayout(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    private lateinit var viewPager2 : ViewPager2

    private var parentVp2: ViewPager2? = null

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop / 2

    private var startX = 0f
    private var startY = 0f

    init {
        // 找到vp2 parent
        postDelayed({
            val start = System.currentTimeMillis()
            logd("find start ")
            viewPager2 = getChildAt(0) as ViewPager2
            findViewPager2()
            logd("find end ${System.currentTimeMillis() - start}ms")
        }, 100) // 延迟一点时间，确保能找到vp2 parent

    }

    /**
     * 找到嵌套的父vp2
     */
    private fun findViewPager2() {
        var theParent = parent
        while (theParent != null) {
            if (theParent is ViewPager2) {
                parentVp2 = theParent
                logd("found vp2 parent")
                break
            }
            theParent = theParent.parent
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                // 先禁止父view拦截事件
                parent.requestDisallowInterceptTouchEvent(true)
                // 禁止vp2 parent滑动
                if (parentVp2 != null) {
                    parentVp2!!.isUserInputEnabled = false
                }
                logd("ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = event.x
                val endY = event.x
                val distanceX = abs(endX - startX)
                val distanceY = abs(endY - startY)
                // 水平滑动
                if (viewPager2.orientation == RecyclerView.HORIZONTAL && (distanceX > touchSlop && distanceX > distanceY)) {
                    // 判断当前vp2能否继续滑动
                    val canScroll = canScroll(endX - startY < 0)
                    // 判断是否需要拦截事件
                    parent.requestDisallowInterceptTouchEvent(canScroll)
                    // 判断vp2 parent是否可以滑动
                    if (parentVp2 != null) {
                        parentVp2!!.isUserInputEnabled = !canScroll
                    }
                } else {
                    // 竖直方向不处理
//                    parent.requestDisallowInterceptTouchEvent(false)
//                    if (parentVp2 != null) {
//                        parentVp2!!.isUserInputEnabled = true
//                    }
                }

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 状态复原
                parent.requestDisallowInterceptTouchEvent(
                    false
                )
                if (parentVp2 != null) {
                    parentVp2!!.isUserInputEnabled = true
                    logd("ACTION_UP")
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    /**
     * 判断是否可以滑动: 左滑则判断当前item是不是最后一个；右滑则判断是不是第一个
     * [directionality]: true for left or otherwise for right
     */
    private fun canScroll(directionLeft: Boolean): Boolean {
        parentVp2 ?: return kotlin.run {
            false
        }
        return with((parentVp2!!)) {
            val current = currentItem
            val total = adapter?.itemCount ?: 0
            when {
                // 往左滑，且划到最后一个
                directionLeft && current == total - 1 -> false
                // 往右滑，且滑到第一个
                !directionLeft && current == 0 -> false
                else -> true
            }
        }

    }

}