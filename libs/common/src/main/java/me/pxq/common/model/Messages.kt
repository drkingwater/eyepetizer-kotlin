package me.pxq.common.model

/**
 * Description: 推送消息实体类
 * Author : pxq
 * Date : 2020/8/23 5:52 PM
 */
data class Messages(val messageList: List<Message>, val nextPageUrl: String?)

data class Message(
    val actionUrl: String,
    val content: String,
    val date: Long,
    val icon: String,
    val id: Int,
//    val ifPush: Boolean,
//    val pushStatus: Int,
    val title: String,
//    val uid: Any,
    val viewed: Boolean
)





