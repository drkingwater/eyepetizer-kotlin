package me.pxq.player

import android.content.Context
import android.net.Uri
import android.view.TextureView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * Description: 播放器实体
 * Author : pxq
 * Date : 2020/8/5 10:41 PM
 */
class ExPlayer(private val player: SimpleExoPlayer, private val context: Context) {

    private val _playState = MutableLiveData<Player.State>()

    //播放状态
    val playState: LiveData<Player.State> = MutableLiveData()

    fun setTextureView(textureView: TextureView) {
        player.setVideoTextureView(textureView)
    }

    fun prepare(url: String) {
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "Eyepetizer")
        )
        // This is the MediaSource representing the media to be played.
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(url))
        // Prepare the player with the source.
        player.prepare(videoSource)
        player.playWhenReady = true
        player.volume = 100f
    }

    fun release(){
        player.release()
    }

}