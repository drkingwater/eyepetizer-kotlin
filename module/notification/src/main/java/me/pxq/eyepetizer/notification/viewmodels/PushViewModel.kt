package me.pxq.eyepetizer.notification.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import me.pxq.framework.model.Messages
import me.pxq.framework.viewmodel.BaseViewModel
import me.pxq.eyepetizer.notification.repository.NotificationRepository
import me.pxq.network.requestFlow
import me.pxq.utils.loge

/**
 * Description: 推送VM
 * Author : pxq
 * Date : 2020/8/23 5:24 PM
 */
@ExperimentalCoroutinesApi
class PushViewModel(private val repository: NotificationRepository) : BaseViewModel() {

    private var nextPageUrl: String? = null

    private val _pushData = MutableLiveData<Messages>()
    val pushData: LiveData<Messages> = _pushData

    // 下一页数据
    private val _nextData = MutableLiveData<Messages>()
    val nextData: LiveData<Messages> = _nextData

    override fun fetchData() {
        requestFlow({
            repository.fetchNotificationPush()
        }, {
            nextPageUrl = it.nextPageUrl
            _pushData.value = it
        }, onStart = {
            _onRefreshing.value = true
            _onError.value = false
        }, onComplete = {
            _onRefreshing.value = false
        }, onError = {
            _onError.value = true
        }).launchIn(viewModelScope)
    }

    /**
     * 请求下一页数据
     */
    override fun fetchNext() {
        nextPageUrl?.run {
            // 防止刷新过快
            val url = this
            nextPageUrl = null
            requestFlow({
                repository.fetchNotificationPush(url)
            }, {
                nextPageUrl = it.nextPageUrl
                _nextData.value = it
            }).launchIn(viewModelScope)
        } ?: kotlin.run {
            loge("没有数据了")
        }

    }

}