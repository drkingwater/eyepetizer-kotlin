package me.pxq.eyepetizer.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import me.pxq.common.model.HomePage
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.network.requestFlow
import me.pxq.utils.loge

/**
 * Description: 首页-推荐 viewModel
 * Author : pxq
 * Date : 2020/7/18 3:29 PM
 */
@ExperimentalCoroutinesApi
class DailyViewModel(private val repository: HomeRepository) : BaseViewModel() {

    //下一页数据url
    private var nextPage: String? = null

    //下一页的数据
    private val _nextData = MutableLiveData<HomePage>()
    val nextData: LiveData<HomePage> = _nextData

    //首页日报数据
    private val _dailyData: MutableLiveData<HomePage> = MutableLiveData()
    val dailyData: LiveData<HomePage> = _dailyData

    /**
     * 获取首页-日报数据
     */
    override fun fetchData() {
        requestFlow({
            repository.fetchDaily()
        }, {
            nextPage = it.nextPageUrl
            _dailyData.value = it
        }, onStart = {
            nextPage = null
            _onRefreshing.value = true
            _onError.value = false
        }, onComplete = {
            _onRefreshing.value = false
        }, onError = {
            _onError.value = true
        }).launchIn(viewModelScope)
    }

    /**
     * 下一页数据
     */
    override fun fetchNext() {
        nextPage?.run {
            val nextPageUrl = this
            nextPage = null
            requestFlow({
                repository.fetchDaily(nextPageUrl)
            }, {
                nextPage = it.nextPageUrl
                _nextData.value = it
            }).launchIn(viewModelScope)
        } ?: kotlin.run {
            loge("没有下一页了...")
        }
    }

}