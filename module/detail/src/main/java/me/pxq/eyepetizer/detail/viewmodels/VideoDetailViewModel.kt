package me.pxq.eyepetizer.detail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import me.pxq.framework.model.HomePage
import me.pxq.framework.model.Item
import me.pxq.framework.viewmodel.BaseViewModel
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
class VideoDetailViewModel(private val repository: VideoDetailRepository) : BaseViewModel() {

    // 首次加载
    private var onAnim = true

    // 相关视频数据
    private val _videoRelated = MutableLiveData<ApiResult<HomePage>>()

    // 相关视频
    val videoRelated: LiveData<ApiResult<HomePage>> = _videoRelated

    // 更多相关视频
    private val _moreRelatedVideos = mutableListOf<Item>()
    val moreRelatedVideos = MutableLiveData<List<Item>>()

    // "查看更多"按钮是否可见
    private val _isLoadMoreVisible = MutableLiveData<Boolean>()
    val isLoadMoreVisible: LiveData<Boolean> = _isLoadMoreVisible

    // 下一页评论
    private var nextRepliesUrl: String? = null

    // 评论数据
    private val _replies = MutableLiveData<ApiResult<HomePage>>()
    val replies: LiveData<ApiResult<HomePage>> = _replies

    // 更多评论数据
    private val _moreReplies = MutableLiveData<HomePage>()
    val moreReplies: LiveData<HomePage> = _moreReplies

    // 获取相关视频
    fun fetchVideoRelated() {
        videoDetail.value?.let {
            viewModelScope.launch {
                _moreRelatedVideos.clear()
                // 延迟一点时间，让动画执行完
                if (onAnim) {
                    onAnim = false
                    delay(200)
                }
                val homePage = async {
                    repository.fetchVideoRelated(it.data.id).apply {
                        if (this is ApiResult.Success && data.itemList.isNotEmpty()) {
                            // 去除无关数据 todo 把字符串改为常量
                            if (data.itemList[0].type != "videoSmallCard") {
                                logd("remove index 0 ")
                                data.itemList.removeAt(0)
                            }
                            // 每次只加载5个
                            if (data.itemList.size > VISIBLE_RELATED_VIDEO_COUNT) {
                                // 把额外的数据保存起来
                                _moreRelatedVideos.addAll(
                                    data.itemList.subList(
                                        VISIBLE_RELATED_VIDEO_COUNT,
                                        data.itemList.size
                                    )
                                )
                                // 去除掉额外的数据
                                data.itemList.removeAll(_moreRelatedVideos)
                                // 设置按钮可见
                                _isLoadMoreVisible.value = true
                            } else {
                                // 设置按钮不可见
                                _isLoadMoreVisible.value = false
                            }
                        }
                    }
                }
                // 获取评论
                val replies = async {
                    repository.fetchVideoReplies(it.data.id).also {
                        if (it is ApiResult.Success) {
                            nextRepliesUrl = it.data.nextPageUrl ?: ""
                        }
                    }
                }
                // 通知推荐更新
                _videoRelated.value = homePage.await()
                // 通知评论更新
                _replies.value = replies.await()

            }
        }
    }

    /**
     * 查看更多视频
     */
    fun loadRelatedVideos() {
        // 加载更多视频数据
        moreRelatedVideos.value = _moreRelatedVideos
        // 设置"查看更多"按钮不可见
        _isLoadMoreVisible.value = false
    }

    /**
     * 加载更多评论数据
     */
    fun fetchMoreVideoReplies() {
        nextRepliesUrl?.run {
            val nextUrl = this@run
            nextRepliesUrl = null
            requestFlow({
                repository.fetchMoreVideoReplies(nextUrl)
            }, {
                nextRepliesUrl = it.nextPageUrl
                _moreReplies.value = it
            }).launchIn(viewModelScope)
        } ?: kotlin.run {
            loge("没有更多评论了...")
        }
    }


    companion object {
        // 首次加载对多可见的推荐视频数据
        const val VISIBLE_RELATED_VIDEO_COUNT = 5
    }

    override fun fetchData() = Unit

    override fun fetchNext() = Unit

}