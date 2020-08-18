package me.pxq.eyepetizer.community.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pxq.common.data.HomePage
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.repository.CommunityRepository
import me.pxq.network.ApiResult
import me.pxq.utils.logd

/**
 * Description: 社区-推荐vm
 * Author : pxq
 * Date : 2020/8/14 10:58 PM
 */
class CommunityViewModel(private val repository: CommunityRepository) : BaseViewModel() {

    // 下一页数据url
    private var _nextPage: String = ""

    // 下一页的数据
    private val _refreshData = MutableLiveData<ApiResult<HomePage>>()
    val refreshData: LiveData<ApiResult<HomePage>> = _refreshData

    // 社区-推荐数据
    private val _recommendData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()
    val recommendData: LiveData<ApiResult<HomePage>> = _recommendData


    /**
     * 获取社区-推荐数据
     */
    override fun fetchData() {
        _nextPage = ""
        viewModelScope.launch(Dispatchers.IO) {
            _onRefreshing.postValue(true)
            _recommendData.postValue(repository.fetchCommunityRecommend().also {
                if (it is ApiResult.Success) {
                    _nextPage = it.data.nextPageUrl ?: ""
                }
            })
            _onRefreshing.postValue(false)
        }
    }

    /**
     * 获取下一页
     */
    fun fetchNextPage() {
        if (_nextPage.isNotEmpty()) {
            val nextPage = _nextPage
            _nextPage = ""
            viewModelScope.launch(Dispatchers.IO) {
                _refreshData.postValue(repository.fetchCommunityRecommend(nextPage).also {
                    if (it is ApiResult.Success) {
                        _nextPage = it.data.nextPageUrl ?: ""
                    }
                })
            }
        } else {
            logd("没有下一页...")
        }
    }

}