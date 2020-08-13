package me.pxq.eyepetizer.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.common.ApiService
import me.pxq.common.data.Item
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.detail.adapters.VideoDetailAdapter
import me.pxq.eyepetizer.detail.databinding.DetailActivityVideoBinding
import me.pxq.eyepetizer.detail.repository.VideoDetailRepository
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModel
import me.pxq.network.ApiResult
import me.pxq.player.PlayerPool
import me.pxq.player.base.PlayerBase
import me.pxq.utils.extensions.load
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/12 10:22 PM
 */
@Route(path = RouterHub.DETAIL_VIDEO)
class VideoDetailActivity : AppCompatActivity() {

    private val videoDetailViewModel =
        VideoDetailViewModel(VideoDetailRepository(ApiService.instance))

    private lateinit var binding: DetailActivityVideoBinding

    private lateinit var videoDetailAdapter: VideoDetailAdapter

    private var videoPlayer: PlayerBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DetailActivityVideoBinding>(
            this,
            R.layout.detail_activity_video
        ).also {
            binding = it
            with(binding.rvVideoDetail) {
                layoutManager =
                    LinearLayoutManager(this@VideoDetailActivity, RecyclerView.VERTICAL, false)
                adapter = VideoDetailAdapter(videoDetailViewModel).also { adapter ->
                    videoDetailAdapter = adapter
                }
                // todo 更多评论
//                setOnBottomListener {
////                    logd(" detail activity on bottom ... ")
//                    videoDetailViewModel.fetchMoreVideoReplies()
//                }
            }
        }
        // 观察数据变化
        observe()

        // 获取传参
        intent.extras?.run {
            getSerializable("video_detail")?.run {
                videoDetailViewModel.videoDetail.value = this as Item
            }
        }
    }

    private fun observe() {
        // 视频信息刷新
        videoDetailViewModel.videoDetail.observe(this) {

            // 播放视频
            videoPlayer?.run {
                release()
            }
            videoPlayer = PlayerPool.get(this).apply {
                // 生命周期控制
                lifecycleOwner = this@VideoDetailActivity
                // prepare后自动播放
                autoPlay = true
                // surface
                textureView = binding.videoTexture
                // 准备播放
                prepare(it.data.playUrl)
            }


            // 加载视频cover
            binding.ivCover.load(it.data.cover.detail)

            // 加载背景图
            binding.ivBg.load(
                it.data.cover.blurred,
                placeHolderId = me.pxq.common.R.drawable.shape_bg_album_loading
            )

            // 先隐藏rv
            binding.rvVideoDetail.visibility = View.GONE
            // position = 0 视频详细信息
            with(videoDetailAdapter) {
                count = 1
                videoDetail = it
            }
            // 视频信息更新,获取相关视频
            videoDetailViewModel.fetchVideoRelated()
        }
        // 相关视频
        videoDetailViewModel.videoRelated.observe(this) {

            when (it) {
                // position = 1 推荐视频
                is ApiResult.Success -> {
                    with(videoDetailAdapter) {
                        relatedVideos.clear()
                        relatedVideos.addAll(it.data.itemList)
                        // adapter count数量+1
                        count = 2
                    }

                }
                is ApiResult.Error -> {
                    loge(it.exception)
                }
            }
        }
        // 查看更多视频
        videoDetailViewModel.moreRelatedVideos.observe(this) {
            videoDetailAdapter.onMoreRelatedVideosLoaded(it)
        }
        // 查看更多 按钮隐藏控制
        videoDetailViewModel.isLoadMoreVisible.observe(this) {
            videoDetailAdapter.setLoadMoreRelatedVisible(it)
        }
        // 评论
        videoDetailViewModel.replies.observe(this) {
            when (it) {
                // position = 2 评论
                is ApiResult.Success -> {
                    with(videoDetailAdapter) {
                        replies.clear()
                        replies.addAll(it.data.itemList)
                        // adapter count数量+1
                        count++
                    }
                }
                is ApiResult.Error -> {
                    loge(it.exception)
                }
            }
            // 请求完所有数据，显示
            binding.rvVideoDetail.scrollToPosition(0)
            binding.rvVideoDetail.visibility = View.VISIBLE
            videoDetailAdapter.notifyDataSetChanged()
        }
        // 更多评论
        videoDetailViewModel.moreReplies.observe(this) {
            when (it) {
                is ApiResult.Success -> {
                    videoDetailAdapter.onMoreRepliesLoaded(it.data.itemList)
                }
                is ApiResult.Error -> {
                    loge(it.exception)
                }
            }
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, me.pxq.common.R.anim.slide_bottom_out)
    }
}