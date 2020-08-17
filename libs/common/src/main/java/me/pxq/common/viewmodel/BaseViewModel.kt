package me.pxq.common.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.pxq.common.data.Item
import me.pxq.utils.logd

/**
 * Description: viewModel基类，处理点击事件等
 * Author : pxq
 * Date : 2020/8/4 9:11 PM
 */
abstract class BaseViewModel : ViewModel() {

    val videoDetail = MutableLiveData<Item>()


    /**
     * 首次进入获取数据
     */
    abstract fun fetchData()

    /**
     * 网络错误
     * 点击重试
     */
    open fun retryOnError() {
        fetchData()
    }

    /**
     * 处理带action的view
     */
    fun execAction(view: View, actionUrl: String?) {
        actionUrl?.run {
            logd(actionUrl)
        }

    }

    /**
     * 点击关注
     */
    fun follow(view: View, item: Item) {
        logd("关注: ${item.data.text}")
    }

    /**
     * 跳转至详情页
     */
    fun navigateToVideo(view: View, item: Item) {
        logd("播放详情页：${item.data.type}")
        videoDetail.value = item
    }

    /**
     * 刷新推荐
     */
    fun refreshToCommend(view: View, item: Item) {
        logd("刷新:${item.data.refreshUrl}")
    }

    /**
     * 跳转至画廊
     */
    fun navigateToPicture(view: View, itemList: List<Item>, index: Int) {
        logd("画廊:${itemList.size} index : $index")
    }


}