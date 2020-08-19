package me.pxq.eyepetizer.main.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import me.pxq.eyepetizer.main.R
import me.pxq.utils.extensions.load

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/19 8:44 PM
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private var animatorSet: AnimatorSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView = ImageView(this).apply {
            scaleType = ImageView.ScaleType.FIT_XY
        }
        setContentView(imageView)
        imageView.load(R.drawable.v4_0_version)
    }

    override fun onResume() {
        super.onResume()
        startAnim(imageView)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
        }
        // 隐藏状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        animatorSet?.cancel()
    }

    private fun startAnim(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f)
        animatorSet = AnimatorSet()
        with(animatorSet!!) {
            playTogether(scaleX, scaleY)
            addListener(onEnd = {
                MainActivity.start(this@SplashActivity)
                finish()
            })
            duration = 3000
            start()
        }
    }

}