package me.pxq.eyepetizer.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import me.pxq.common.model.HomePage
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.network.ApiResult
import me.pxq.network.requestFlow

/**
 * Description: 首页-推荐 viewModel
 * Author : pxq
 * Date : 2020/7/18 3:29 PM
 */
@ExperimentalCoroutinesApi
class DiscoveryViewModel(private val repository: HomeRepository) : BaseViewModel() {

    //首页-发现数据
    private val _discoveryData: MutableLiveData<HomePage> = MutableLiveData()
    val discoveryData: LiveData<HomePage> = _discoveryData

    /**
     * 获取首页-发现数据
     */
    override fun fetchData() {
        requestFlow({
            repository.fetchDiscovery()
        }, {
            _discoveryData.value = it
        }, onStart = {
            _onRefreshing.value = true
            _onError.value = false
        }, onComplete = {
            _onRefreshing.value = false
        }, onError = {
            _onError.value = true
        }).launchIn(viewModelScope)
    }

    override fun fetchNext() = Unit


}