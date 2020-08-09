package me.pxq.eyepetizer.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.common.ApiService
import me.pxq.common.R
import me.pxq.common.data.Item
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.detail.adapters.VideoDetailAdapter
import me.pxq.eyepetizer.detail.databinding.DetailActivityVideoBinding
import me.pxq.eyepetizer.detail.repository.VideoDetailRepository
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModel
import me.pxq.network.ApiResult
import me.pxq.utils.extensions.load
import me.pxq.utils.logd
import me.pxq.utils.loge

/**
 * Description: 视频详情页Fragment
 * Author : pxq
 * Date : 2020/8/9 2:27 PM
 */
@Route(path = RouterHub.DETAIL_VIDEO)
class VideoDetailFragment : Fragment() {

    private val videoDetailViewModel =
        VideoDetailViewModel(VideoDetailRepository(ApiService.instance))

    private lateinit var binding: DetailActivityVideoBinding

    private lateinit var videoDetailAdapter: VideoDetailAdapter

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
        videoDetailViewModel.videoDetail.observe(this) {

            // 加载视频cover
            binding.ivCover.load(it.data.cover.detail)

            // 加载背景图
            binding.ivBg.load(it.data.cover.blurred)

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
        }
        // 相关视频
        videoDetailViewModel.videoRelated.observe(this) {

            when (it) {
                is ApiResult.Success -> {
                    with(videoDetailAdapter) {
                        relatedVideos.clear()
                        relatedVideos.addAll(it.data.itemList)
                        // adapter count数量+1
                        count = 2
                        notifyItemInserted(1)
                    }

                }
                is ApiResult.Error -> {
                    loge(it.exception)
                }
            }
        }
    }


}