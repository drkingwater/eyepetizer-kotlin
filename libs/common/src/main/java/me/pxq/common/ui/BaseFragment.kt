package me.pxq.common.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import me.pxq.common.R
import me.pxq.common.model.Item
import me.pxq.common.router.RouterHub
import me.pxq.common.viewmodel.BaseViewModel
import me.pxq.utils.logd


/**
 * Description:
 * Author : pxq
 * Date : 2020/8/9 2:16 AM
 */
abstract class BaseFragment : Fragment() {

    /**
     * 跳转至详情页
     */
    fun navigateToVideo(item: Item, view: View? = null) {
        logd("播放详情页：${item.data.type}")

        // 针对activity
        ARouter.getInstance().build(RouterHub.DETAIL_VIDEO)
            .withSerializable(RouterHub.DETAIL_VIDEO_PARAM, item)
            .navigation(requireContext())
    }

    fun navigateToAlbum(item: Item) {
        ARouter.getInstance().build(RouterHub.DETAIL_ALBUM)
            .withSerializable(RouterHub.DETAIL_ALBUM_PARAM, item)
            .navigation(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 处理屏幕旋转等不请求重新请求数据
        if (savedInstanceState == null) {
            fetchData()
        }
    }

    abstract fun fetchData()

}