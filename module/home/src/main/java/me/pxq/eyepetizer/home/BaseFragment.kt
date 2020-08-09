package me.pxq.eyepetizer.home

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import me.pxq.common.data.Item
import me.pxq.common.router.RouterHub
import me.pxq.utils.logd


/**
 * Description:
 * Author : pxq
 * Date : 2020/8/9 2:16 AM
 */
abstract class BaseFragment : Fragment() {

    val DETAIL_FRAGMENT_TAG = "detail_fragment_tag"

    /**
     * 跳转至详情页
     */
    fun navigateToVideo(item: Item) {
        logd("播放详情页：${item.data.type}")
        ARouter.getInstance().build(RouterHub.DETAIL_VIDEO)
            .withSerializable("video_detail", item)
            .navigation()?.run {
                this as Fragment
                this@BaseFragment.requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true) // Optimize for shared element transition
//                    .addSharedElement(transitioningView, transitioningView.getTransitionName())
                    .setCustomAnimations(
                        me.pxq.common.R.anim.slide_bottom_in,
                        me.pxq.common.R.anim.slide_bottom_out,
                        me.pxq.common.R.anim.slide_bottom_in,
                        me.pxq.common.R.anim.slide_bottom_out
                    )
                    .add(
                        R.id.fragment_container, this, DETAIL_FRAGMENT_TAG
                    )
                    .addToBackStack(DETAIL_FRAGMENT_TAG)
                    .commit()
            }
    }

}