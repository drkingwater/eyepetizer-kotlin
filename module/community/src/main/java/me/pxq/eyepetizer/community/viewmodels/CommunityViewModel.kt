package me.pxq.eyepetizer.community.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.pxq.common.model.HomePage
import me.pxq.common.model.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.repository.CommunityRepository
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 社区-推荐vm
 * Author : pxq
 * Date : 2020/8/14 10:58 PM
 */
class CommunityViewModel(private val repository: CommunityRepository) : BaseViewModel() {

    val albumDetail = MutableLiveData<Item>()

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
        viewModelScope.launch {
            _onRefreshing.value = true
            _recommendData.value = repository.fetchCommunityRecommend().also {
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
                _refreshData.value = repository.fetchCommunityRecommend(nextPage).also {
                    if (it is ApiResult.Success) {
                        _nextPage = it.data.nextPageUrl ?: ""
                    }
                }
            }
        } else {
            logd("没有下一页...")
        }
    }

    /**
     * 跳转至图片详情页，需要item.data.dataType == FollowCard
     */
    fun navigateToAlbum(view: View, item: Item) {
        if ("FollowCard" == item.data.dataType) {
            logd("图片详情页：${item.type}")
            albumDetail.value = item
        } else {
            loge(Exception("item类型不匹配：${item.type} - ${item.data.dataType}"))
        }
    }


}