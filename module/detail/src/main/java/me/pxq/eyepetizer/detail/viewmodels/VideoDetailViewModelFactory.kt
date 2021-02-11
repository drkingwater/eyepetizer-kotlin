package me.pxq.eyepetizer.detail.viewmodels

import androidx.lifecycle.*
import me.pxq.framework.ApiService
import me.pxq.eyepetizer.detail.repository.VideoDetailRepository

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