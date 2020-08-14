package me.pxq.launch.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ui.PlayerView
import me.pxq.player.base.PlayerBase
import me.pxq.player.PlayerPool
import me.pxq.utils.logd

class MainActivity : AppCompatActivity() {

    companion object {
        const val URL =
            "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=202808&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss"
    }

    lateinit var textureView: TextureView
    lateinit var playerView: PlayerView
    var player: PlayerBase? = null

    // 播放器类型
    private var playerCore: Int = PlayerBase.PLAYER_EXO

    // 循环播放
    private var repeat: Boolean = false
    // 自动播放
    private var autoPlay: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView = findViewById(R.id.texture_view)
        findViewById<RadioGroup>(R.id.radio_group).apply {
            setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radio_exo -> playerCore = PlayerBase.PLAYER_EXO
                    else -> playerCore = PlayerBase.PLAYER_MEDIA
                }
            }
        }
        findViewById<CheckBox>(R.id.cb_auto_play).apply {
            isSelected = autoPlay
            setOnCheckedChangeListener { _, checked ->
                autoPlay = checked
            }
        }
        playerView = findViewById(R.id.player_view)


    }

    fun play(view: View) {
        player?.start()
    }

    fun pause(view: View) {
        player?.pause()
    }

    fun prepare(view: View) {
        player?.release()
        //初始化播放器
        player = PlayerPool.get(this, playerCore)
        //设置播放器参数
        with(player!!) {
            textureView = textureView
            // 自动播放
            autoPlay = autoPlay
            // 重复
            repeat(repeat)

            playState.observe(this@MainActivity, Observer {
                logd("play state : $it")
            })
            prepare(URL)
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
    }


    fun release(view: View) {
        player?.release()
    }

    fun full(view: View) {
        
    }


}
