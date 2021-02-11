package me.pxq.eyepetizer.community.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import me.pxq.framework.model.HomePage
import me.pxq.framework.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.repository.CommunityRepository
import me.pxq.network.requestFlow
import me.pxq.utils.logd

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/22 5:49 PM
 */
@ExperimentalCoroutinesApi
class FollowViewModel(private val repository: CommunityRepository) : BaseViewModel() {

    // 是否首次加载，首次加载延迟一点时间,否则可能导致卡顿
    private var isFirst = true

    // 首次加载数据
    private val _followData = MutableLiveData<HomePage>()
    val followData: LiveData<HomePage> = _followData

    // 下一页数据url
    private var _nextPage: String = ""

    // 下一页的数据
    private val _nextData = MutableLiveData<HomePage>()
    val nextData: LiveData<HomePage> = _nextData

    /**
     * 获取社区-推荐数据
     */
    override fun fetchData() {
        requestFlow({
            if (isFirst) { // 首次加载，延迟一点时间让fragment切换完毕
                isFirst = false
                delay(100)
            }
            repository.fetchCommunityFollow()
        }, onSuccess = {
            _nextPage = it.nextPageUrl ?: ""
            _followData.value = it
        }, onStart = {
            _nextPage = ""
            _onRefreshing.value = true
            _onError.value = false
        }, onComplete = {
            _onRefreshing.value = false
        }, onError = {
            _onError.value = true
        }).launchIn(viewModelScope)
    }

    /**
     * 获取下一页
     */
    override fun fetchNext() {
        if (_nextPage.isNotEmpty()) {
            val nextPage = _nextPage
            _nextPage = ""
            requestFlow({
                repository.fetchCommunityFollow(nextPage)
            }, {
                _nextPage = it.nextPageUrl ?: ""
                _nextData.value = it
            }).launchIn(viewModelScope)
        } else {
            logd("没有下一页...")
        }
    }
}