package me.pxq.eyepetizer.main.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.main.R
import me.pxq.common.ui.view.EyeBottomNavView

/**
 * Description: 主页
 * Author : pxq
 * Date : 2020/7/22 9:52 PM
 */
class MainActivity : AppCompatActivity() {

    private val fragmentTags = listOf("Home", "Community")
    private var currentFragmentTag = ""
    private var oldFragmentTag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main)

//        savedInstanceState?.run {
//            currentFragmentTag = SpUtil.getString(this@MainActivity, KEY_CURRENT_FRAGMENT_TAG)
//            oldFragmentTag = SpUtil.getString(this@MainActivity, KEY_OLD_FRAGMENT_TAG)
//            logi("savedInstanceState get : $currentFragmentTag $oldFragmentTag")
//        }

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
            // 设置选中监听
            setOnNavigationItemSelectedListener {
                // 修改选中图标
                setCheckedIcon(it.itemId)
                // 切换fragment
                //todo 切换fragment
                selectFragment(it.itemId)?.run {
                    // 重复点击，不处理
                    if (oldFragmentTag == currentFragmentTag && currentFragmentTag != "") {
                        return@run
                    }
                    val oldFragment = supportFragmentManager.findFragmentByTag(oldFragmentTag)
                    supportFragmentManager.beginTransaction()
                        .apply {
                            // 显示选中的Fragment
                            if (!this@run.isAdded) {
                                add(R.id.fragment_container, this@run, currentFragmentTag)
                            } else {
                                show(this@run)
                            }
                            // 隐藏之前的Fragment
                            if (oldFragment != null) {
                                hide(oldFragment)
                            }
                        }
                        .commit().also {
                            // 重新赋值
                            oldFragmentTag = currentFragmentTag
                        }
                }
                true
            }
            //默认选择"首页"
            selectedItemId = R.id.bottom_nav_menu_item_home
        }
    }

    /**
     * 切换Fragment
     * [selectedId] : 选中的选项id
     * 返回Fragment或者null，如果没有找到
     */
    private fun selectFragment(selectedId: Int): Fragment? = when (selectedId) {
        R.id.bottom_nav_menu_item_home -> {
            currentFragmentTag = fragmentTags[0]
            var homeFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
            if (homeFragment == null) {
                ARouter.getInstance().build(RouterHub.MAIN_HONE).navigation()?.run {
                    homeFragment = this as Fragment
                }
            }
            homeFragment
        }
        R.id.bottom_nav_menu_item_community -> {
            currentFragmentTag = fragmentTags[1]
            var communityFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
            if (communityFragment == null) {
                ARouter.getInstance().build(RouterHub.MAIN_COMMUNITY).navigation()?.run {
                    communityFragment = this as Fragment
                }
            }
            communityFragment
        }

        else -> null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        loge("onSaveInstance put : $currentFragmentTag $oldFragmentTag")
//        SpUtil.putString(this, KEY_CURRENT_FRAGMENT_TAG, currentFragmentTag)
//        SpUtil.putString(this, KEY_OLD_FRAGMENT_TAG, currentFragmentTag)
    }

    companion object{
        const val KEY_CURRENT_FRAGMENT_TAG = "key_current_tag"
        const val KEY_OLD_FRAGMENT_TAG = "key_old_tag"

        @JvmStatic
        fun start(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }


}