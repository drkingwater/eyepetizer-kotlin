package me.pxq.player.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/6 10:04 PM
 */
abstract class AbsPlayer : PlayerBase {


    //播放状态
    private val _playState: MutableLiveData<Int> = MutableLiveData()

    //是否自动播放
    protected var autoPlay: Boolean = false

    //播放状态
    override val playState: LiveData<Int>
        get() = _playState

    protected fun onStateChange(@PlayerBase.State state: Int) {
        _playState.value = state
    }


}