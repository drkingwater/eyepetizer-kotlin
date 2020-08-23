package me.pxq.eyepetizer.notification.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.pxq.common.model.HomePage
import me.pxq.common.model.Messages
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.notification.repository.NotificationRepository
import me.pxq.network.ApiResult
import me.pxq.utils.loge

/**
 * Description: 推送VM
 * Author : pxq
 * Date : 2020/8/23 5:24 PM
 */
class PushViewModel(private val repository: NotificationRepository) : BaseViewModel() {

    private var nextPageUrl: String? = null

    private val _pushData = MutableLiveData<ApiResult<Messages>>()
    val pushData: LiveData<ApiResult<Messages>> = _pushData

    // 下一页数据
    private val _nextData = MutableLiveData<ApiResult<Messages>>()
    val nextData: LiveData<ApiResult<Messages>> = _nextData

    override fun fetchData() {
        viewModelScope.launch {
            nextPageUrl = null
            _onRefreshing.value = true

            _pushData.value = repository.fetchNotificationPush().also {
                if (it is ApiResult.Success) {
                    nextPageUrl = it.data.nextPageUrl
                }
            }

            _onRefreshing.value = false
        }
    }

    /**
     * 请求下一页数据
     */
    fun fetchNextData() {
        nextPageUrl?.let {
            // 防止刷新过快
            val url = nextPageUrl!!
            nextPageUrl = null
            viewModelScope.launch {
                _nextData.value = repository.fetchNotificationPush(url).also {
                    if (it is ApiResult.Success) {
                        nextPageUrl = it.data.nextPageUrl
                    }
                }
            }
        } ?: kotlin.run {
            loge("没有数据了")
        }

    }

}