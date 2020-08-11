package me.pxq.player.media

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.view.Surface
import android.view.TextureView
import me.pxq.player.base.AbsPlayer
import me.pxq.player.base.PlayerBase

/**
 * Description: MediaPlayer播放器封装
 * Author : pxq
 * Date : 2020/8/6 10:02 PM
 */
internal class EMediaPlayer(private val context: Context) : AbsPlayer(),
    TextureView.SurfaceTextureListener {

    // 自动播放
    override var autoPlay: Boolean = false

    // 设置surface
    override var textureView: TextureView? = null
        set(value) {
            field = value
            textureView?.run {
                if (isAvailable) {
                    mediaPlayer.setSurface(Surface(surfaceTexture))
                }
                surfaceTextureListener = this@EMediaPlayer
            }
        }


    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener {
            onPlayStateChange(PlayerBase.READY)
            if (autoPlay) {
                start()
            }
        }
        setOnErrorListener { _, _, _ ->
            onPlayStateChange(PlayerBase.ERROR)
            true
        }
        setOnCompletionListener {
            onPlayStateChange(PlayerBase.COMPLETED)
        }
        setOnBufferingUpdateListener { _, _ ->

        }
    }

//    override fun setTextureView(textureView: TextureView) {
//        if (textureView.isAvailable) {
//            mediaPlayer.setSurface(Surface(textureView.surfaceTexture))
//        }
//        textureView.surfaceTextureListener = this
//    }

    override fun repeat(repeat: Boolean) {
        mediaPlayer.isLooping = repeat
    }

    /**
     * Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "audio/mp3"); // change content type if necessary
    headers.put("Accept-Ranges", "bytes");
    headers.put("Status", "206");
    headers.put("Cache-control", "no-cache");
     */
    override fun prepare(url: String) {
        mediaPlayer.setDataSource(
            context.applicationContext,
            Uri.parse(url),
            //todo 播放到一半停止播放的bug
            mapOf("Cache-control" to "no-cache", "Accept-Ranges" to "bytes", "Status" to "206")
        )
        mediaPlayer.prepareAsync()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
        try {
            super.release()
            mediaPlayer.stop()
            mediaPlayer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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