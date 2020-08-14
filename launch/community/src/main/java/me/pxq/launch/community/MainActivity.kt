package me.pxq.launch.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.pxq.eyepetizer.community.CommunityFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment =
            supportFragmentManager.findFragmentByTag(CommunityFragment::class.java.simpleName)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    CommunityFragment(),
                    CommunityFragment::class.java.simpleName
                ).commit()
        }
    }
}
