package me.pxq.launch.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import androidx.lifecycle.Observer
import me.pxq.player.ExPlayer
import me.pxq.player.PlayerPool
import me.pxq.utils.logd

class MainActivity : AppCompatActivity() {

    lateinit var textureView: TextureView
    var player: ExPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView = findViewById(R.id.texture_view)
    }

    fun play(view: View) {
        player = PlayerPool.get(this)
        with(player!!){
            setTextureView(textureView)
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

    companion object {
        const val URL =
            "http://ali.cdn.kaiyanapp.com/1562841227064_13408871_480x270.mp4?auth_key=1596644177-0-0-43388f23bc2e8480bfec6895fd9aea20"
    }
}
