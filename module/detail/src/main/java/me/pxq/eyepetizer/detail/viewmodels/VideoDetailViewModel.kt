package me.pxq.eyepetizer.detail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.pxq.common.data.HomePage
import me.pxq.common.data.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.detail.repository.VideoDetailRepository
import me.pxq.network.ApiResult
import me.pxq.utils.logd

/**
 * Description: 视频详情页vm
 * Author : pxq
 * Date : 2020/8/8 8:50 PM
 */
class VideoDetailViewModel(private val repository: VideoDetailRepository) : BaseViewModel() {

    private val _videoRelated = MutableLiveData<ApiResult<HomePage>>()
    val videoRelated: LiveData<ApiResult<HomePage>> = _videoRelated

    // 获取相关视频
    fun fetchVideoRelated() {
        videoDetail.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                // 延迟一点时间，让动画执行完
                delay(300)
                val homePage = repository.fetchVideoRelated(it.data.id).run {
                    if (this is ApiResult.Success){
                        if(data.itemList.isNotEmpty() && data.itemList[0].type != "videoSmallCard"){
                            logd("remove index 0 ")
                            data.itemList.removeAt(0)
                        }
                    }
                    this
                }
                _videoRelated.postValue(homePage)
            }
        }

    }

}