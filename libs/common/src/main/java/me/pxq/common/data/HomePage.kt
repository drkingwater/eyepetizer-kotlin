package me.pxq.common.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.pxq.common.db.converter.HomeItemConverter

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/15 10:26 PM
 */
@Entity(tableName = "home")
data class HomePage(
    @PrimaryKey
    val date: Long,
    val adExist: Boolean = false,
    val count: Int = 0,
    val itemList: List<Item> = listOf(),
    val lastStartId: Int = 0,
    val nextPageUrl: String = "",
    val nextPublishTime: Long = 0,
    val refreshCount: Int = 0,
    val total: Int = 0
)

data class Item(
    val adIndex: Int,
    val data: Data,
    val id: Int,
    val tag: String,
    val type: String
)

data class Data(
    val ad: Boolean,
//    val adTrack: List<Any>,
    val author: Author,
//    val brandWebsiteInfo: Any,
//    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
//    val descriptionPgc: Any,
    val duration: Int,
//    val favoriteAdTrack: Any,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: String,
    val labelList: List<String>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: List<PlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: Provider,
    val reallyCollected: Boolean,
//    val recallSource: Any,
    val releaseTime: Long,
//    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
//    val shareAdTrack: Any,
    val slogan: String,
    val src: String,
    val subtitles: List<String>,
    val tags: List<Tag>,
    val thumbPlayUrl: String,
    val title: String,
//    val titlePgc: Any,
    val type: String,
//    val waterMarks: Any,
//    val webAdTrack: Any,
    val webUrl: WebUrl
)

data class Author(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: Follow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: Shield,
    val videoNum: Int
)

data class Consumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class Cover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class PlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<Url>,
    val width: Int
)

data class Provider(
    val alias: String,
    val icon: String,
    val name: String
)

data class Tag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
//    val childTagIdList: Any,
//    val childTagList: Any,
    val communityIndex: Int,
    val desc: Any,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
//    val newestEndTime: Any,
    val tagRecType: String
)

data class WebUrl(
    val forWeibo: String,
    val raw: String
)

data class Follow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class Shield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class Url(
    val name: String,
    val size: Int,
    val url: String
)
/**
 * "count": 14,
"total": 0,
"nextPageUrl": "http://baobab.kaiyanapp.com/api/v4/tabs/selected?date=1594602000000&num=2&page=2",
"adExist": false,
"date": 1594774800000,
"nextPublishTime": 1594861200000,
"dialog": null,
"topIssue": null,
"refreshCount": 0,
"lastStartId": 0
 */
