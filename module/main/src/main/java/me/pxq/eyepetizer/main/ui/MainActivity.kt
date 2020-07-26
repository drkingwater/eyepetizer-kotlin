package me.pxq.eyepetizer.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import me.pxq.eyepetizer.main.R
import me.pxq.eyepetizer.main.ui.view.EyeBottomNavView

/**
 * Description: 主页
 * Author : pxq
 * Date : 2020/7/22 9:52 PM
 */
class MainActivity : AppCompatActivity() {

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
                when(it.itemId){
                    //todo 切换fragment
                }
                true
            }
            //默认选择"首页"
            selectedItemId = R.id.bottom_nav_menu_item_home
        }
    }


}