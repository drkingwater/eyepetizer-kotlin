package me.pxq.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


/**
 * Description: Player缓冲池？
 * Author : pxq
 * Date : 2020/8/5 10:34 PM
 */
object PlayerPool {

    private const val MAX_POOL_SIZE = 3   //最大缓存3个播放器

    fun get(context: Context) = ExPlayer(
        SimpleExoPlayer.Builder(context)
            .build(), context
    )

}