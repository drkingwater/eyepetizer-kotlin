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
    //上左、上右圆角
    const val TYPE_TOP = 1

    //上右圆角
    const val TYPE_TOP_RIGHT = 2

    //上左圆角
    const val TYPE_TOP_LEFT = 3

    //下右圆角
    const val TYPE_BOTTOM_RIGHT = 4

    //左上、左下圆角
    const val TYPE_LEFT = 5


    /**
     * 绘制半圆角图片(顶部两个圆角)
     */
    fun roundedCorners(
        pool: BitmapPool,
        inBitmap: Bitmap,
        roundingRadius: Int,
        type: Int
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

        val rect = when (type) {
            TYPE_TOP -> RectF(  //上面两个角圆角
                0f,
                0f,
                result.width.toFloat(),
                result.height.toFloat() + roundingRadius  //设置目标高度比原图高，让底部的角不被裁剪
            )
            TYPE_TOP_RIGHT -> RectF(  //右上角圆角
                -roundingRadius.toFloat(),
                0f,
                result.width.toFloat() + roundingRadius,
                result.height.toFloat() + roundingRadius
            )
            TYPE_TOP_LEFT -> RectF(  //左上角圆角
                0f,
                0f,
                result.width.toFloat() + 1,
                result.height.toFloat() + 1
            )
            TYPE_BOTTOM_RIGHT -> RectF(  //右下角圆角
                -roundingRadius.toFloat(),
                0f,
                result.width.toFloat(),
                result.height.toFloat()
            )
            TYPE_LEFT -> RectF(  //左边圆角
                0f,
                0f,
                result.width.toFloat() + roundingRadius,
                result.height.toFloat()
            )
            else -> RectF(
                0f,
                0f,
                result.width.toFloat(),
                result.height.toFloat() + roundingRadius
            )

        }
        val canvas = Canvas(result)
        //todo 设置缩放比例
//        if (scaleWidth != 0) {
//            val matrix = Matrix().apply {
////                preScale(1.2f, 1.0f)
////                postScale(1.2f, 1.0f)
//            }
//            canvas.setMatrix(matrix)
//        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//        drawRoundedCorners(canvas, paint, rect)
        canvas.drawRoundRect(rect, roundingRadius.toFloat(), roundingRadius.toFloat(), paint)

        clear(canvas)

        if (toTransform != inBitmap) {
            pool.put(toTransform)
        }

        return result
    }

    private fun getPath(roundingRadius : Float, width : Float, height:Float){
        val path = Path()
        val rectF = RectF(0f, 0f, width, height)

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