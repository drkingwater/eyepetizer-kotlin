package me.pxq.eyepetizer.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.pxq.framework.R
import me.pxq.framework.model.Item
import me.pxq.eyepetizer.detail.adapters.VideoDetailAdapter
import me.pxq.eyepetizer.detail.databinding.DetailActivityVideoBinding
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModel
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModelFactory
import me.pxq.network.ApiResult
import me.pxq.player.PlayerPool
import me.pxq.player.base.PlayerBase
import me.pxq.utils.extensions.load
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 视频详情页Fragment
 * Author : pxq
 * Date : 2020/8/9 2:27 PM
 */

class VideoDetailFragment : Fragment() {

    private val videoDetailViewModel = ViewModelProvider(this, VideoDetailViewModelFactory.get()).get(VideoDetailViewModel::class.java)

    private lateinit var binding: DetailActivityVideoBinding

    private lateinit var videoDetailAdapter: VideoDetailAdapter

    private var videoPlayer: PlayerBase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logd("onCreateView")
        return DetailActivityVideoBinding.inflate(inflater, container, false).run {
            binding = this
            with(binding.rvVideoDetail) {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = VideoDetailAdapter(videoDetailViewModel).also {
                    videoDetailAdapter = it
                }
            }
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logd("onViewCreated")
        observe()

        arguments?.run {
            getSerializable("video_detail")?.run {
                videoDetailViewModel.videoDetail.value = this as Item
            }
        }
    }


    private fun observe() {
        // 视频信息刷新
        videoDetailViewModel.videoDetail.observe(viewLifecycleOwner, Observer {

            // 播放视频
            videoPlayer?.run {
                release()
            }
            videoPlayer = PlayerPool.get(requireContext()).apply {
                // 生命周期控制
                lifecycleOwner = this@VideoDetailFragment
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
                placeHolderId = R.drawable.shape_bg_album_loading
            )

            // 先隐藏
            binding.rvVideoDetail.visibility = View.GONE
            // 滚到顶部
//            binding.rvVideoDetail.smoothScrollToPosition(0)

            // adapter count数量+1
            with(videoDetailAdapter) {
                count = 1
                videoDetail = it
                notifyDataSetChanged()
            }
            // 显示
            binding.rvVideoDetail.visibility = View.VISIBLE

            // 视频信息更新,获取相关视频
            videoDetailViewModel.fetchVideoRelated()
        })
        // 相关视频
        videoDetailViewModel.videoRelated.observe(viewLifecycleOwner, Observer {

            when (it) {
                is ApiResult.Success -> {
                    with(videoDetailAdapter) {
                        relatedVideos.clear()
                        relatedVideos.addAll(it.data.itemList)
                        // adapter count数量+1
                        count = 2
//                        notifyItemInserted(1)
                    }

                }
                is ApiResult.Error -> {
                    loge(it.exception)
                }
            }
        })
        // 查看更多视频
        videoDetailViewModel.moreRelatedVideos.observe(viewLifecycleOwner, Observer {
            videoDetailAdapter.onMoreRelatedVideosLoaded(it)
        })
        videoDetailViewModel.isLoadMoreVisible.observe(viewLifecycleOwner, Observer {
            videoDetailAdapter.setLoadMoreRelatedVisible(it)
        })
        // 评论
        videoDetailViewModel.replies.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    with(videoDetailAdapter) {
                        replies.clear()
                        replies.addAll(it.data.itemList)
                        // adapter count数量+1
                        count++
                        logd("replay $count")
//                        notifyItemRangeInserted(1, count)
//                        notifyItemInserted(count - 1)
                        notifyDataSetChanged()
                    }
                }
                is ApiResult.Error -> {
                    loge(it.exception)
                }
            }
        })
        // 更多评论
        videoDetailViewModel.moreReplies.observe(viewLifecycleOwner, Observer {
            videoDetailAdapter.onMoreRepliesLoaded(it.itemList)
        })

    }

}