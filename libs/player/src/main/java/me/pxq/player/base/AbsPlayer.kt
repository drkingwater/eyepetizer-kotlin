package me.pxq.player.base

import androidx.lifecycle.*
import me.pxq.utils.logd

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/6 10:04 PM
 */
abstract class AbsPlayer : PlayerBase, LifecycleEventObserver {

    // 生命周期控制
    override var lifecycleOwner: LifecycleOwner? = null
        set(value) {
            field = value?.apply {
                lifecycle.addObserver(this@AbsPlayer)
            }
        }

    //播放状态
    private val _playState: MutableLiveData<Int> = MutableLiveData()

    //播放状态
    override val playState: LiveData<Int>
        get() = _playState

    /**
     * 播放状态变化
     */
    protected fun onPlayStateChange(@PlayerBase.State state: Int) {
        _playState.value = state
    }

    /**
     * 生命周期变化
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {//暂停
                pause()
                logd("player should pause...")
            }
            Lifecycle.Event.ON_RESUME -> {  //继续播放
                start()
                logd("player should resume...")
            }
            Lifecycle.Event.ON_DESTROY -> {
                release()
                source.lifecycle.removeObserver(this)
                logd("player should release...")
            }
        }
    }

    override fun release() {
        lifecycleOwner?.run {
            lifecycle.removeObserver(this@AbsPlayer)
        }
    }


}