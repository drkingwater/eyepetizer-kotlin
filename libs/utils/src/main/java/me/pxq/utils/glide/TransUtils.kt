package me.pxq.utils.glide

import android.graphics.*
import android.os.Build
import androidx.core.graphics.withMatrix
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool

/**
 * Description: Glide 图片变换Transformation工具类
 * Author : pxq
 * Date : 2020/8/1 2:02 PM
 */
object TransUtils {

    /**
     * 绘制半圆角图片(顶部两个圆角)
     */
    fun topRoundedCorners(
        pool: BitmapPool,
        inBitmap: Bitmap,
        roundingRadius: Int,
        scaleWidth: Int = 0
    ): Bitmap {

        // Alpha is required for this transformation.
        val safeConfig = getAlphaSafeConfig(inBitmap)
        val toTransform = getAlphaSafeBitmap(pool, inBitmap)
        val result = pool[toTransform.width, toTransform.height, safeConfig]

        result.setHasAlpha(true)

        val shader =
            BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val paint = Paint().apply {
            isAntiAlias = true
            this.shader = shader
        }
        //设置目标高度比原图高，让底部的角不被裁剪
        val rect = RectF(0f, 0f, result.width.toFloat(), result.height.toFloat() + roundingRadius)

        val canvas = Canvas(result)
        //设置缩放比例
        if (scaleWidth != 0) {
            val matrix = Matrix().apply {
//                preScale(1.2f, 1.0f)
//                postScale(1.2f, 1.0f)
            }
            canvas.setMatrix(matrix)
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//        drawRoundedCorners(canvas, paint, rect)
        canvas.drawRoundRect(rect, roundingRadius.toFloat(), roundingRadius.toFloat(), paint)
        clear(canvas)

        if (toTransform != inBitmap) {
            pool.put(toTransform)
        }

        return result
    }

    // Avoids warnings in M+.
    private fun clear(canvas: Canvas) {
        canvas.setBitmap(null)
    }

    private fun getAlphaSafeConfig(inBitmap: Bitmap): Bitmap.Config {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Avoid short circuiting the sdk check.
            if (Bitmap.Config.RGBA_F16 == inBitmap.config) { // NOPMD
                return Bitmap.Config.RGBA_F16
            }
        }
        return Bitmap.Config.ARGB_8888
    }

    private fun getAlphaSafeBitmap(
        pool: BitmapPool, maybeAlphaSafe: Bitmap
    ): Bitmap {
        val safeConfig = getAlphaSafeConfig(maybeAlphaSafe)
        if (safeConfig == maybeAlphaSafe.config) {
            return maybeAlphaSafe
        }
        val argbBitmap =
            pool[maybeAlphaSafe.width, maybeAlphaSafe.height, safeConfig]
        Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0f, 0f, null /*paint*/)

        // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
        // when we're finished with it.
        return argbBitmap
    }

}