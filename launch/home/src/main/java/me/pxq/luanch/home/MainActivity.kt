package me.pxq.luanch.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.pxq.eyepetizer.home.ui.IndexFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, IndexFragment()).commit()
    }
}
