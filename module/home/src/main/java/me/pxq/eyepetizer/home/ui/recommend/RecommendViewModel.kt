package me.pxq.eyepetizer.home.ui.recommend

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pxq.common.data.HomePage
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.eyepetizer.home.viewmodel.BaseViewModel
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 首页-推荐 viewModel
 * Author : pxq
 * Date : 2020/7/18 3:29 PM
 */
class RecommendViewModel(private val repository: HomeRepository) : BaseViewModel() {

    //下一页数据url
    private var nextPage: String = ""

    //下一页的数据
    val refreshData = MutableLiveData<ApiResult<HomePage>>()

    //首页推荐数据
    val homeData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()

    /**
     * 获取首页-推荐数据
     */
    fun fetchRecommend() {
        fetchData(true)
    }

    /**
     * 下一页数据
     */
    fun fetchNextPage() {
        fetchData(false, nextPage)
    }

    /**
     * 请求数据
     */
    private fun fetchData(isFirst : Boolean, url: String = "") {
        viewModelScope.launch {
            if (!isFirst && url.isEmpty()){
                loge("没有数据了...")
                return@launch
            }
            repository.fetchRecommend(url).also {
                if (it is ApiResult.Success) {
                    logd("nextUrl : ${it.data.nextPageUrl}")
                    nextPage = it.data.nextPageUrl ?: ""
                }
            }.let {
                //更新数据
                if (url.isEmpty()) {
                    homeData.value = it
                } else {
                    refreshData.value = it
                }
            }
        }
    }




}