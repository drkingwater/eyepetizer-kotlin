package me.pxq.player.media

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import me.pxq.player.base.AbsPlayer
import me.pxq.player.base.PlayerBase

/**
 * Description: MediaPlayer播放器封装
 * Author : pxq
 * Date : 2020/8/6 10:02 PM
 */
internal class EMediaPlayer : AbsPlayer(), TextureView.SurfaceTextureListener {

    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener {
            onStateChange(PlayerBase.READY)
            if (autoPlay){
                start()
            }
        }
        setOnErrorListener { _, _, _ ->
            onStateChange(PlayerBase.ERROR)
            true
        }
        setOnCompletionListener {
            onStateChange(PlayerBase.COMPLETED)
        }
        setOnBufferingUpdateListener { _, _ ->

        }
    }

    override fun setTextureView(textureView: TextureView) {
        if (textureView.isAvailable){
            mediaPlayer.setSurface(Surface(textureView.surfaceTexture))
        }
        textureView.surfaceTextureListener = this
    }

    override fun autoPlay(auto: Boolean) {
        autoPlay = auto
    }

    override fun repeat(repeat: Boolean) {
        mediaPlayer.isLooping = repeat
    }

    override fun prepare(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        return false
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        surface?.run {
            mediaPlayer.setSurface(Surface(this))
        }
    }
}