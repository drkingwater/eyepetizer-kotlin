package me.pxq.framework.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import me.pxq.framework.R
import me.pxq.utils.logd
import kotlin.math.abs

/**
 * Description: Vp2实现的Banner，处理嵌套vp2时水平滑动冲突
 * <p>
 * 1、只能包含vp2子view
 * 2、可设置Banner Item显示时的左右间距和Item之间的间隔
 * </p>
 * Author : pxq
 * Date : 2020/8/15 11:53 PM
 */
class Vp2BannerLayout(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    private lateinit var viewPager2: ViewPager2
    private var init = false

    // 嵌套的vp2
    private var parentVp2: ViewPager2? = null

    // 滑动最小距离
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop / 2

    // 处理滑动冲突，记录屏幕按下位置
    private var startX = 0f
    private var startY = 0f

    // 自定义属性
    private var bannerMarginStart = 0
    private var bannerMarginEnd = 0
    private var pageMargin = 0

    init {
        // 获取自定义属性值
        initAttr(attributeSet)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!init) {
            logd("onAttachedToWindow")
            val start = System.currentTimeMillis()
            logd("find start ")
            viewPager2 = getChildAt(0) as ViewPager2
            viewPager2.offscreenPageLimit = 1
            viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == SCROLL_STATE_IDLE) {
                        setVp2ParentState(true)
                    }
                }
            })
            // 设置Banner边距
            setRvPadding()
            // 寻找嵌套的父vp2
            findVp2Parent()
            logd("find end ${System.currentTimeMillis() - start}ms")
            init = true
        }


    }

    /**
     * 获取自定义属性的值
     */
    private fun initAttr(attributeSet: AttributeSet?) {
        context.obtainStyledAttributes(attributeSet, R.styleable.Vp2BannerLayout).run {
            if (hasValue(R.styleable.Vp2BannerLayout_bannerMarginStart)) {
                bannerMarginStart =
                    getDimension(R.styleable.Vp2BannerLayout_bannerMarginStart, 0f).toInt()
            }
            if (hasValue(R.styleable.Vp2BannerLayout_bannerMarginEnd)) {
                bannerMarginEnd =
                    getDimension(R.styleable.Vp2BannerLayout_bannerMarginEnd, 0f).toInt()
            }
            if (hasValue(R.styleable.Vp2BannerLayout_pageMargin)) {
                pageMargin = getDimension(R.styleable.Vp2BannerLayout_pageMargin, 0f).toInt()
            }

            recycle()
        }
    }

    /**
     * 设置banner间距
     */
    private fun setRvPadding() {
        // 设置item左右边距
        logd("$bannerMarginStart $bannerMarginEnd $pageMargin")
        with((viewPager2.getChildAt(0) as RecyclerView)) {
            setPadding(
                bannerMarginStart,
                0,
                bannerMarginEnd, 0
            )
            clipToPadding = false
        }
        // 设置item间距
        if (pageMargin > 0) {
            viewPager2.setPageTransformer(
                MarginPageTransformer(pageMargin)
            )
        }
    }

    /**
     * 找到嵌套的父vp2
     */
    private fun findVp2Parent() {
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
                setVp2ParentState(false)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = event.x
                val endY = event.x
                val distanceX = abs(endX - startX)
                val distanceY = abs(endY - startY)
                // 水平滑动
                if (viewPager2.orientation == RecyclerView.HORIZONTAL && (distanceX > touchSlop && distanceX > distanceY)) {
                    // 判断当前vp2能否继续滑动
//                    val canScroll = canScroll(endX - startX < 0)
                    // 判断是否需要拦截事件
//                    parent.requestDisallowInterceptTouchEvent(canScroll)
                    // 判断是否需要把滑动交给vp2 parent
//                    setVp2ParentState(!canScroll)
                } else {
                    // 竖直方向不处理
                    parent.requestDisallowInterceptTouchEvent(false)
                }

            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 状态复原
                parent.requestDisallowInterceptTouchEvent(false)
                setVp2ParentState(true)
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

    /**
     * 设置vp2 parent滑动状态
     * [enable] true : 可滑动 false : 不可滑动
     */
    private fun setVp2ParentState(enable: Boolean) {
        if (parentVp2 != null) {
            parentVp2!!.isUserInputEnabled = enable
        }
    }

}