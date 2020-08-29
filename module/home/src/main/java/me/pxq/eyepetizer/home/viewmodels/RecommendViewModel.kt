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

/**
 * Description: 首页-推荐 viewModel
 * Author : pxq
 * Date : 2020/7/18 3:29 PM
 */
@ExperimentalCoroutinesApi
class RecommendViewModel(private val repository: HomeRepository) : BaseViewModel() {

    //下一页数据url
    private var nextPage: String? = null

    //下一页的数据
    private val _nextData = MutableLiveData<HomePage>()
    val nextData: LiveData<HomePage> = _nextData

    //首页推荐数据
    private val _homeData: MutableLiveData<HomePage> = MutableLiveData()
    val homeData: LiveData<HomePage> = _homeData

    /**
     * 获取首页-推荐数据
     */
    override fun fetchData() {
        _onRefreshing.value = true
        requestFlow({
            repository.fetchRecommend()
        }, {
            nextPage = it.nextPageUrl
            _homeData.value = it
        }, onStart = {
            _onError.value = false
            _onRefreshing.value = true
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
            val url = this
            nextPage = null
            requestFlow({
                repository.fetchRecommend(url)
            }, {
                nextPage = it.nextPageUrl
                _nextData.value = it
            }).launchIn(viewModelScope)
        }
    }

}