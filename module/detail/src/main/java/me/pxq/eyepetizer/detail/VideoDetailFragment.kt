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
import me.pxq.common.R
import me.pxq.common.data.Item
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.detail.adapters.VideoDetailAdapter
import me.pxq.eyepetizer.detail.databinding.DetailActivityVideoBinding
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModel
import me.pxq.utils.extensions.load
import me.pxq.utils.logd

/**
 * Description: 视频详情页Fragment
 * Author : pxq
 * Date : 2020/8/9 2:27 PM
 */
@Route(path = RouterHub.DETAIL_VIDEO)
class VideoDetailFragment : Fragment(), Observer<Item> {

    private val videoDetailViewModel = VideoDetailViewModel()

    private lateinit var binding: DetailActivityVideoBinding

    private lateinit var videoDetailAdapter: VideoDetailAdapter

//    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
//        return if (enter){
//            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_bottom_in)
//        } else {
//            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
//
//        }
//    }

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
                adapter = VideoDetailAdapter().also {
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

    override fun onDestroyView() {
        super.onDestroyView()
        videoDetailViewModel.videoDetail.removeObserver(this)
    }

    private fun observe() {
        videoDetailViewModel.videoDetail.observe(requireActivity(), this)
    }

    override fun onChanged(it: Item?) {
        it?.run {
            binding.ivBg.load(it.data.cover.blurred, placeHolderId = R.color.black)
            videoDetailAdapter.items.add(0, it)
            logd("size : ${videoDetailAdapter.items.size}")
            videoDetailAdapter.notifyItemChanged(0)
        }

    }

}