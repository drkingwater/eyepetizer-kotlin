package me.pxq.eyepetizer.detail.viewmodels

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import me.pxq.common.ApiService
import me.pxq.common.db.EyeDatabase
import me.pxq.common.model.HomePage
import me.pxq.common.model.Item
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.eyepetizer.detail.repository.VideoDetailRepository
import me.pxq.network.ApiResult
import me.pxq.network.requestFlow
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 视频详情页vm
 * Author : pxq
 * Date : 2020/8/8 8:50 PM
 */
class VideoDetailViewModelFactory(private val repository: VideoDetailRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideoDetailViewModel(repository) as T
    }

    companion object {
        fun get(): VideoDetailViewModelFactory =
            VideoDetailViewModelFactory(VideoDetailRepository(ApiService.instance))
    }
}