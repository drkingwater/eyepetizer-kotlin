package me.pxq.eyepetizer.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pxq.common.data.HomePage
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.network.ApiResult

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/18 3:29 PM
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    //首页推荐数据
    val homeData: MutableLiveData<ApiResult<HomePage>> = MutableLiveData()

    fun fetchHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            //更新数据
            homeData.postValue(repository.getHomeData(true))
        }
    }

}