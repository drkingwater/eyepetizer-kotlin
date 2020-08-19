package me.pxq.eyepetizer.home.ui.discovery

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

    //下一页数据url
    private var nextPage: String = ""

    //下一页的数据
    val refreshData = MutableLiveData<ApiResult<HomePage>>()

    //首页推荐数据
    val discoveryData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()

    /**
     * 获取首页-发现数据
     */
    override fun fetchData() {
        _onRefreshing.value = true
        fetchDiscoveryData(true)
    }

    /**
     * 下一页数据
     */
    fun fetchNextPage() {
        fetchDiscoveryData(false, nextPage)
    }

    /**
     *
     */
    private fun fetchDiscoveryData(isFirst : Boolean, url: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isFirst && url.isEmpty()){
                loge("没有数据了...")
                return@launch
            }
            repository.fetchDiscovery().also {
                if (it is ApiResult.Success) {
                    logd("nextUrl : ${it.data.nextPageUrl}")
                    nextPage = it.data.nextPageUrl?:""
                }
            }.let {
                //更新数据
                if (url.isEmpty()) {
                    discoveryData.postValue(it)
                    _onRefreshing.postValue(false)
                } else {
                    refreshData.postValue(it)
                }
            }
        }
    }


}