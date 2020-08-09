package me.pxq.eyepetizer.detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.common.data.Item
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.detail.adapters.VideoDetailAdapter
import me.pxq.eyepetizer.detail.viewmodels.VideoDetailViewModel
import me.pxq.utils.extensions.load

/**
 * Description: 视频播放详情页
 * Author : pxq
 * Date : 2020/8/8 1:04 AM
 */

class VideoDetailActivity : AppCompatActivity() {

    private lateinit var ivBg : ImageView

    private val videoDetailViewModel by lazy {
        VideoDetailViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity_video)
        findViewById<RecyclerView>(R.id.rv_video_detail).apply {
            layoutManager =
                LinearLayoutManager(this@VideoDetailActivity, RecyclerView.VERTICAL, false)
            adapter = VideoDetailAdapter().also {
                observe(it)
            }

        }

        ivBg = findViewById(R.id.iv_bg)

        intent.extras?.run {
            getSerializable("video_detail")?.run {
                videoDetailViewModel.videoDetail.value = this as Item
            }
        }
    }

    private fun observe(adapter: VideoDetailAdapter) {
        videoDetailViewModel.videoDetail.observe(this, Observer {
            ivBg.load(it.data.cover.blurred, placeHolderId = me.pxq.common.R.color.black)
            adapter.items.add(0, it)
            adapter.notifyItemChanged(0)
        })
    }

}