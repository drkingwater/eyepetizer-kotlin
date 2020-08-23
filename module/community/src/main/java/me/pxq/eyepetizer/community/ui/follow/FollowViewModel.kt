package me.pxq.eyepetizer.community.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.pxq.common.model.HomePage
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.repository.CommunityRepository
import me.pxq.network.ApiResult
import me.pxq.utils.logd

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/22 5:49 PM
 */
class FollowViewModel(private val repository: CommunityRepository) : BaseViewModel() {

    // 是否首次加载，首次加载延迟一点时间,否则可能导致卡顿
    private var isFirst = true

    // 首次加载数据
    private val _followData = MutableLiveData<ApiResult<HomePage>>()
    val followData: LiveData<ApiResult<HomePage>> = _followData

    // 下一页数据url
    private var _nextPage: String = ""

    // 下一页的数据
    private val _refreshData = MutableLiveData<ApiResult<HomePage>>()
    val refreshData: LiveData<ApiResult<HomePage>> = _refreshData

    /**
     * 获取社区-推荐数据
     */
    override fun fetchData() {
        _nextPage = ""
        viewModelScope.launch {
            _onRefreshing.value = true
            if (isFirst) { // 首次加载，延迟一点时间让fragment切换完毕
                isFirst = false
                delay(100)
            }
            _followData.value = repository.fetchCommunityFollow().also {
                if (it is ApiResult.Success) {
                    _nextPage = it.data.nextPageUrl ?: ""
                }
            }
            _onRefreshing.value = false
        }
    }

    /**
     * 获取下一页
     */
    fun fetchNextPage() {
        if (_nextPage.isNotEmpty()) {
            val nextPage = _nextPage
            _nextPage = ""
            viewModelScope.launch {
                _refreshData.value = repository.fetchCommunityFollow(nextPage).also {
                    if (it is ApiResult.Success) {
                        _nextPage = it.data.nextPageUrl ?: ""
                    }
                }
            }
        } else {
            logd("没有下一页...")
        }
    }
}