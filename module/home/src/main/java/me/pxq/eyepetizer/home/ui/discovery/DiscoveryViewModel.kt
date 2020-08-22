package me.pxq.eyepetizer.home.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pxq.common.data.HomePage
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 首页-推荐 viewModel
 * Author : pxq
 * Date : 2020/7/18 3:29 PM
 */
class DiscoveryViewModel(private val repository: HomeRepository) : BaseViewModel() {

    //首页-发现数据
    private val _discoveryData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()
    val discoveryData: LiveData<ApiResult<HomePage>> = _discoveryData

    /**
     * 获取首页-发现数据
     */
    override fun fetchData() {
        viewModelScope.launch {
            _onRefreshing.value = true
            _discoveryData.value = repository.fetchDiscovery()
            _onRefreshing.value = false
        }
    }


}