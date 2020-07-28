package me.pxq.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import java.security.MessageDigest
import java.util.*

/**
 * Description: 设备相关工具类
 * Author : pxq
 * Date : 2020/7/28 9:01 PM
 */

object DeviceUtil {

    lateinit var application: Application

    /**
     * 屏幕尺寸,如：1280X720
     */
    val DISPLAY_SIZE by lazy {
        application.resources.displayMetrics.run {
            "${widthPixels}X${heightPixels}"
        }
    }

    /**
     * [Build.MODEL]或者unknown
     */
    val DEVICE_MODEL: String
        get() = Build.MODEL.ifEmpty { "unknown" }

    /**
     * 设备序列号
     */
    val DEVICE_SERIAL: String by lazy {
        //本地取序列号
        var serial = DeviceIdUtil.getDeviceId(application).also {
            logd("serial $it")
        }
        if (serial.isNullOrEmpty()) {
            //随机生成一个序列号并保存到本地
            serial = UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.CHINA).also {
                SpUtil.putString(application, "deviceSerial", it)
            }
        }
        serial
    }

    /**
     * Android版本
     * @see [Build.VERSION.SDK_INT]
     */
    val SDK_INT = Build.VERSION.SDK_INT


//    /**
//     * @author xc
//     * @date 2018/11/16
//     * @desc
//     */
//    class DeviceIdUtil {
//        /**
//         * 获得设备硬件标识
//         *
//         * @param context 上下文
//         * @return 设备硬件标识
//         */
//        fun getDeviceId(context: Context) {
//            val sbDeviceId = StringBuilder()
//
//            //获得AndroidId（无需权限）
//            val androidid = getAndroidId(context);
//            //获得设备序列号（无需权限）
//            val serial = getSERIAL()
//            //获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）
//            val uuid = getDeviceUUID().replace("-", "");
//
//            //追加androidid
//            if (androidid != null && androidid.length() > 0) {
//                sbDeviceId.append(androidid);
//                sbDeviceId.append("|");
//            }
//            //追加serial
//            if (serial != null && serial.length() > 0) {
//                sbDeviceId.append(serial);
//                sbDeviceId.append("|");
//            }
//            //追加硬件uuid
//            if (uuid != null && uuid.length() > 0) {
//                sbDeviceId.append(uuid);
//            }
//
//            //生成SHA1，统一DeviceId长度
//            if (sbDeviceId.isNotEmpty()) {
//                try {
//                    byte[] hash = getHashByString (sbDeviceId.toString());
//                    String sha1 = bytesToHex (hash);
//                    if (sha1 != null && sha1.length() > 0) {
//                        //返回最终的DeviceId
//                        return sha1;
//                    }
//                } catch (ex: Exception) {
//                    ex.printStackTrace();
//                }
//            }
//
//            //如果以上硬件标识数据均无法获得，
//            //则DeviceId默认使用系统随机数，这样保证DeviceId不为空
//            return UUID.randomUUID().toString().replace("-", "");
//        }
//
//        /**
//         * 获得设备的AndroidId
//         *
//         * @param context 上下文
//         * @return 设备的AndroidId
//         */
//        @SuppressLint("HardwareIds")
//        fun getAndroidId(context: Context): String {
//            try {
//                return Settings.Secure.getString(
//                    context.contentResolver,
//                    Settings.Secure.ANDROID_ID
//                );
//            } catch (ex: Exception) {
//                ex.printStackTrace();
//            }
//            return "";
//        }
//
//        /**
//         * 获得设备序列号（如：WTK7N16923005607）, 个别设备无法获取
//         *
//         * @return 设备序列号
//         */
//        fun getSERIAL(): String {
//            try {
//                return Build.SERIAL;
//            } catch (ex: Exception) {
//                ex.printStackTrace();
//            }
//            return "";
//        }
//
//        /**
//         * 获得设备硬件uuid
//         * 使用硬件信息，计算出一个随机数
//         *
//         * @return 设备硬件uuid
//         */
//        fun getDeviceUUID(): String {
//            try {
//                val dev = "3883756" +
//                        Build.BOARD.length % 10 +
//                        Build.BRAND.length % 10 +
//                        Build.DEVICE.length % 10 +
//                        Build.HARDWARE.length % 10 +
//                        Build.ID.length % 10 +
//                        Build.MODEL.length % 10 +
//                        Build.PRODUCT.length % 10
//                return UUID(
//                    dev.hashCode().toLong(),
//                    Build.MODEL.hashCode().toLong()
//                ).toString();
//            } catch (ex: Exception) {
//                ex.printStackTrace();
//                return "";
//            }
//        }
//
//        /**
//         * 取SHA1
//         * @param data 数据
//         * @return 对应的hash值
//         */
//        fun getHashByString(data: String): ByteArray {
//            return try {
//                val messageDigest = MessageDigest.getInstance("SHA1");
//                messageDigest.reset();
//                messageDigest.update(data.toByteArray());
//                messageDigest.digest();
//            } catch (e: Exception) {
//                "".toByteArray();
//            }
//        }
//
//        /**
//         * 转16进制字符串
//         * @param data 数据
//         * @return 16进制字符串
//         */
//        fun bytesToHex(data: ByteArray) {
//            val sb = StringBuilder();
//            var stmp = "";
//            data.forEach {
//                stmp =
//            }
//            for (int n = 0; n < data.length; n++){
//                stmp = (Integer.toHexString(data[n] & 0xFF));
//                if (stmp.length() == 1)
//                    sb.append("0");
//                sb.append(stmp);
//            }
//            return sb.toString().toUpperCase(Locale.CHINA);
//        }
//    }


}