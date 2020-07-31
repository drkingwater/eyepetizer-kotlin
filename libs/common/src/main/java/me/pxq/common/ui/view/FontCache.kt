package me.pxq.common.ui.view

import android.content.Context
import android.graphics.Typeface
import java.lang.ref.WeakReference

/**
 * Description: 字体缓存类，缓存字体避免卡顿
 * Author : pxq
 * Date : 2020/7/30 9:36 PM
 */
object FontCache {

    //字体样式
    const val FZLAN_L = "FZLanL"
    const val FZLAN_D = "FZLanD"
    const val FUTURA = "Futura"
    const val DIN = "DIN"
    const val LOBSTER = "Lobster"

    @JvmStatic
    private val fontCache = mutableMapOf<String, WeakReference<Typeface>>()

    /**
     * 根据[typefaceName]拿到相应的字体信息[Typeface]并缓存
     */
    @JvmStatic
    fun getTypeface(context: Context, typefaceName: String): Typeface? {
        var typeface = fontCache[typefaceName]?.run {
            get()
        }
        if (typeface == null) {
            //根据字体名拿到字体信息
            typeface = when (typefaceName) {
                FZLAN_L ->
                    Typeface.createFromAsset(
                        context.assets,
                        "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
                    )
                FZLAN_D ->
                    Typeface.createFromAsset(
                        context.assets,
                        "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
                    )
                FUTURA ->
                    Typeface.createFromAsset(
                        context.assets,
                        "fonts/Futura-CondensedMedium.ttf"
                    )
                DIN ->
                    Typeface.createFromAsset(
                        context.assets,
                        "fonts/DIN-Condensed-Bold.ttf"
                    )
                LOBSTER ->
                    Typeface.createFromAsset(
                        context.assets,
                        "fonts/Lobster-1.4.otf"
                    )
                else -> null
            }?.also {
                //缓存字体信息
                fontCache[typefaceName] = WeakReference(it)
            }
        }
        return typeface

    }

}