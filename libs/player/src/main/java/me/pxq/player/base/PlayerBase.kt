package me.pxq.player.base

import android.view.TextureView
import androidx.annotation.IntDef
import androidx.lifecycle.LiveData

/**
 * Description: 播放器抽象接口
 * Author : pxq
 * Date : 2020/8/6 9:25 PM
 */
interface PlayerBase {

    val playState: LiveData<Int>

    fun setTextureView(textureView: TextureView)
    // 是否自动播放
    fun autoPlay(auto: Boolean)
    // 是否循环播放
    fun repeat(repeat : Boolean)
    // 准备播放资源
    fun prepare(url: String)
    // 开始播放
    fun start()
    // 暂停播放
    fun pause()
    // 释放资源
    fun release()


    @IntDef(
        PLAYER_MEDIA,
        PLAYER_EXO
    )
    @Target(AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class PlayerCore

    @IntDef(
        READY,
        COMPLETED,
        ERROR,
        BUFFERING
    )
    @Target(AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State

    companion object {
        //MediaPlayer
        const val PLAYER_MEDIA = 100
        //ExoPlayer
        const val PLAYER_EXO = 200

        //可以播放
        const val READY = 1

        //播放完毕
        const val COMPLETED = 2

        //正在缓冲
        const val BUFFERING = 3

        //播放失败
        const val ERROR = 99
    }

}