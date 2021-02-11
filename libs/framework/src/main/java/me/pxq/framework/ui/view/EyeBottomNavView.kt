package me.pxq.framework.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Description: 自定义BNV来修改选中图标
 * <p>
 *     用法：
 *     1、setCheckIcons(map)
 *     2、setSelectedItemId(id)
 * </p>
 * Author : pxq
 * Date : 2020/7/26 5:32 PM
 * @see BottomNavigationView
 */
class EyeBottomNavView(context: Context, attributeSet: AttributeSet? = null) :
    BottomNavigationView(context, attributeSet) {

    //保存itemId和候选图片的map
    var candidateIcons: MutableMap<Int, Drawable>? = null

    //上次选中的itemId
    var oldItemId: Int = -1

    override fun setSelectedItemId(itemId: Int) {
        super.setSelectedItemId(itemId)
        //修改选中状态
        setCheckedIcon(itemId)
    }

    /**
     * 修改选中状态为[itemId]，同时取消[oldItemId]的选中状态
     */
    fun setCheckedIcon(itemId: Int) {
        //重复选中不处理
        if (itemId == oldItemId) return
        //修改选中图标
        //取消上次选中的图标
        if (oldItemId != -1) {
            switchIcon(oldItemId)
        }
        //显示新的选中
        switchIcon(itemId)
        //保存选中状态
        oldItemId = itemId
    }

    /**
     * 根据[itemId]设置选中的图标
     */
    private fun switchIcon(itemId: Int) {
        candidateIcons ?: return
        //交换正在显示的图片和候选图片
        val drawable = candidateIcons!![itemId]
        val menuItem = menu.findItem(itemId)
        candidateIcons!![itemId] = menuItem.icon
        menuItem.icon = drawable
    }

}