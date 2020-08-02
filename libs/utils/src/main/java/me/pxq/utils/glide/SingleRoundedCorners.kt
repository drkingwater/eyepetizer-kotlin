package me.pxq.utils.glide

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Description: 半圆角矩形，左上角、右上角为圆角[roundingRadius]
 * todo : 可以选择设置水平缩放宽度[scaleWidth]
 * Author : pxq
 * Date : 2020/8/1 1:27 PM
 */
class SingleRoundedCorners(private val roundingRadius: Int, private val type:Int, private val scaleWidth: Int = 0) :
    BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return TransUtils.roundedCorners(pool, toTransform, roundingRadius, type)
    }

    override fun equals(other: Any?): Boolean {
        if (other is SingleRoundedCorners) {
            return other.roundingRadius == roundingRadius
        }
        return false
    }

    companion object {
        private const val ID = "me.pxq.utils.glide.RoundedCornersTransformation"
        val ID_BYTES = ID.toByteArray()
    }

}