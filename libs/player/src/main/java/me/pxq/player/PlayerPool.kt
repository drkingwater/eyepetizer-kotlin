package me.pxq.player

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import me.pxq.player.base.PlayerBase
import me.pxq.player.exo.ExPlayer
import me.pxq.player.media.EMediaPlayer


/**
 * Description: Player缓冲池？
 * Author : pxq
 * Date : 2020/8/5 10:34 PM
 */
object PlayerPool {

    private const val MAX_POOL_SIZE = 3   //最大缓存3个播放器

    fun get(context: Context, @PlayerBase.PlayerCore playerCore: Int = PlayerBase.PLAYER_EXO): PlayerBase {
        return when (playerCore) {
            PlayerBase.PLAYER_MEDIA -> EMediaPlayer(context)
            else -> ExPlayer(
                SimpleExoPlayer.Builder(context)
                    .build(), context
            )
        }

    }

}