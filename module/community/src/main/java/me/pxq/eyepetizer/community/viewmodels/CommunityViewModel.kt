package me.pxq.eyepetizer.community.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import me.pxq.common.model.HomePage
import me.pxq.common.model.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.community.repository.CommunityRepository
import me.pxq.network.requestFlow
import me.pxq.utils.logd
import me.pxq.utils.loge
import java.io.Serializable

/**
 * Description: 社区-推荐vm
 * Author : pxq
 * Date : 2020/8/14 10:58 PM
 */
@ExperimentalCoroutinesApi
class CommunityViewModel(private val repository: CommunityRepository) : BaseViewModel(),
    Serializable {

    val albumDetail = MutableLiveData<Item>()

    // 下一页数据url
    private var _nextPage: String? = null

    // 下一页的数据
    private val _nextData = MutableLiveData<HomePage>()
    val nextData: LiveData<HomePage> = _nextData

    // 社区-推荐数据
    private val _recommendData: MutableLiveData<HomePage> = MutableLiveData()
    val recommendData: LiveData<HomePage> = _recommendData


    /**
     * 获取社区-推荐数据
     */
    override fun fetchData() {
        requestFlow({
            repository.fetchCommunityRecommend()
        }, onSuccess = {
            _nextPage = it.nextPageUrl
            _recommendData.value = it
        }, onStart = {
            _nextPage = null
            _onRefreshing.value = true
            _onError.value = false
        }, onComplete = {
            _onRefreshing.value = false
        }, onError = {
            _onError.value = true
        }).launchIn(viewModelScope)
    }


    /**
     * 获取下一页
     */
    override fun fetchNext() {
        _nextPage?.run {
            val nextPage = this
            _nextPage = null
            requestFlow({
                repository.fetchCommunityRecommend(nextPage)
            }, {
                _nextPage = it.nextPageUrl ?: ""
                _nextData.value = it
            }).launchIn(viewModelScope)
        } ?: kotlin.run {
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