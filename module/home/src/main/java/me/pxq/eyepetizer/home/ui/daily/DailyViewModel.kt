package me.pxq.eyepetizer.home.ui.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.pxq.common.model.HomePage
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
class DailyViewModel(private val repository: HomeRepository) : BaseViewModel() {

    //下一页数据url
    private var nextPage: String = ""

    //下一页的数据
    private val _refreshData = MutableLiveData<ApiResult<HomePage>>()
    val refreshData : LiveData<ApiResult<HomePage>> = _refreshData

    //首页日报数据
    private  val _dailyData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()
    val dailyData: LiveData<ApiResult<HomePage>> = _dailyData

    /**
     * 获取首页-日报数据
     */
    override fun fetchData() {
        _onRefreshing.value = true
        fetchDailyData(true)
    }

    /**
     * 下一页数据
     */
    fun fetchNextPage() {
        fetchDailyData(false, nextPage)
        nextPage = ""
    }

    /**
     * 请求数据
     */
    private fun fetchDailyData(isFirst: Boolean, url: String = "") {
        viewModelScope.launch {
            if (!isFirst && url.isEmpty()) {
                loge("没有数据了...")
                return@launch
            }
            repository.fetchDaily(url).also {
                if (it is ApiResult.Success) {
                    logd("nextUrl : ${it.data.nextPageUrl}")
                    nextPage = it.data.nextPageUrl ?: ""
                }
            }.let {
                //更新数据
                if (url.isEmpty()) {
                    _dailyData.value = it
                } else {
                    _refreshData.value = it
                }
                _onRefreshing.value = false
            }
        }
    }


}