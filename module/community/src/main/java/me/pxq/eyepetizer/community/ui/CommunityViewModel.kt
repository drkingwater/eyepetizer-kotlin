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
    private var nextPage: String = ""

    // 下一页的数据
    val refreshData = MutableLiveData<ApiResult<HomePage>>()

    // 社区-推荐数据
    private val _recommendData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()
    val recommendData: LiveData<ApiResult<HomePage>> = _recommendData


    /**
     * 获取社区-推荐数据
     */
    override fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _recommendData.postValue(repository.fetchCommunityRecommend().also {
                logd(it)
            })
        }
    }

}