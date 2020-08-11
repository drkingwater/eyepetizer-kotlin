package me.pxq.player.exo

import android.content.Context
import android.net.Uri
import android.view.TextureView
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import me.pxq.player.base.AbsPlayer
import me.pxq.player.base.PlayerBase

/**
 * Description: ExoPlayer播放器实体
 * Author : pxq
 * Date : 2020/8/5 10:41 PM
 */
internal class ExPlayer(private val player: SimpleExoPlayer, private val context: Context) : AbsPlayer() {

    // 是否自动播放
    override var autoPlay: Boolean = false
        set(value) {
            field = value
            player.playWhenReady = value
        }
    // 设置surface
    override var textureView: TextureView? = null
        set(value) {
            field = value
            player.setVideoTextureView(textureView)
        }

    override fun repeat(repeat: Boolean) {
        player.repeatMode = Player.REPEAT_MODE_ONE
    }

    override fun prepare(url: String) {
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "Eyepetizer")
        )
        // 准备播放源
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(url))
        // 监听播放状态
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> onPlayStateChange(PlayerBase.BUFFERING)
                    Player.STATE_READY -> onPlayStateChange(PlayerBase.READY)
                }
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                onPlayStateChange(PlayerBase.ERROR)
            }
        })
        player.prepare(videoSource)
    }

    override fun start() {
        player.playWhenReady = true
    }

    override fun pause() {
        player.playWhenReady = false
    }

    override fun release() {
        super.release()
        player.release()
    }

}