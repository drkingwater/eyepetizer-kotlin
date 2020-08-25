package me.pxq.common.router

/**
 * Description: ARouter路由表，集中管理
 * Author : pxq
 * Date : 2020/7/20 9:54 PM
 */
object RouterHub {
    // 主页-首页
    const val MAIN_HONE = "/home/index"

    // 主页-社区
    const val MAIN_COMMUNITY = "/community/index"

    // 主页-通知
    const val MAIN_NOTIFICATION = "/notification/index"

    // 视频详情页
    const val DETAIL_VIDEO = "/detail/video"
    const val DETAIL_VIDEO_PARAM = "video_detail"
    const val DETAIL_ALBUM = "/detail/album"
    const val DETAIL_ALBUM_PARAM = "album_detail"

}