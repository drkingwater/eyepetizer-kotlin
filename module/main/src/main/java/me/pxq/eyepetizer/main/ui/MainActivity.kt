package me.pxq.eyepetizer.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.main.R
import me.pxq.eyepetizer.main.ui.view.EyeBottomNavView

/**
 * Description: 主页
 * Author : pxq
 * Date : 2020/7/22 9:52 PM
 */
class MainActivity : AppCompatActivity() {

    private val fragmentTags = listOf("Home", "Community")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main)

        findViewById<EyeBottomNavView>(R.id.bottom_nav_layout)?.apply {
            itemIconTintList = null
            //设置选中图标
            candidateIcons = mutableMapOf(
                R.id.bottom_nav_menu_item_home to ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.main_btn_home_selected
                )!!,
                R.id.bottom_nav_menu_item_community to ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.main_btn_community_selected
                )!!,
                R.id.bottom_nav_menu_item_notification to ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.main_btn_notification_selected
                )!!,
                R.id.bottom_nav_menu_item_mime to ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.main_btn_mine_selected
                )!!
            )
            //设置选中监听
            setOnNavigationItemSelectedListener {
                //修改选中图标
                setCheckedIcon(it.itemId)
                //切换fragment
                //todo 切换fragment
                selectFragment(it.itemId)?.run {
                    supportFragmentManager.beginTransaction()
                        .apply {
                            if (!this@run.isAdded) {
                                add(R.id.fragment_container, this@run, fragmentTags[0])
                            } else {
                                show(this@run)
                            }
                        }
                        .commit()
                }
                true
            }
            //默认选择"首页"
            selectedItemId = R.id.bottom_nav_menu_item_home
        }
    }

    private fun selectFragment(selectedId: Int): Fragment? = when (selectedId) {
        R.id.bottom_nav_menu_item_home -> {
            var homeFragment = supportFragmentManager.findFragmentByTag(fragmentTags[0])
            if (homeFragment == null) {
                ARouter.getInstance().build(RouterHub.MAIN_HONE).navigation().run {
                    homeFragment = this as Fragment
                }
            }
            homeFragment
        }

        else -> null
    }


}