package me.pxq.common.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import me.pxq.common.db.converter.HomeItemConverter
import me.pxq.common.retrofit.CoverTypeAdapter
import java.io.Serializable

/**
 * Description: 首页-推荐 数据类
 * Author : pxq
 * Date : 2020/7/15 10:26 PM
 */
@Entity(tableName = "home")
data class HomePage(
    @PrimaryKey
    val key: Int,
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<Item>,
    val nextPageUrl: String,
    val total: Int
)

data class Item(
    val adIndex: Int,
    val `data`: Data,
    val id: String,
    val tag: Any,
    val type: String
) : Serializable


data class Data(
    val actionUrl: String,
    val ad: Boolean,
    val adTrack: Any,
    val author: Author,
    val backgroundImage: String,
    val bannerList: List<Banner>,
    val bgPicture: String,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: Consumption,
    val content: Item,
    val count: Int,
    @JsonAdapter(CoverTypeAdapter::class)
    val cover: Cover,
    val createTime: Long,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val detail: Detail,
    val expert: Boolean,
    val favoriteAdTrack: Any,
    val follow: Follow,
    val footer: Any,
    val haveReward: Boolean,
    val header: Header,
    val headerTitle: String,
    val headerDescription: String,
    val headerIcon: String,
    val icon: String,
    val iconType: String,
    val id: String,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val ifNewest: Boolean,
    val ifPgc: Boolean,
    val ifShowNotificationIcon: Boolean,
    val itemList: MutableList<Item>,
    val image: String,
    val label: Any,
    val labelList: List<Any>,
    val lastViewTime: Any,
    val library: String,
    val liked: Boolean,
    val likeCount: Int,
    val hot: Boolean,
    val showParentReply: Boolean,
    val showConversationButton: Boolean,
    val medalIcon: Boolean,
    val message: String,
    val newestEndTime: Any,
    val nickname: String,
    val playInfo: List<PlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: Provider,
    val reallyCollected: Boolean,
    val recallSource: String,
    val refreshUrl: String,
    val releaseTime: Long,
    val replyStatus: String,
    val remark: Any,
    val resourceType: String,
    val rightText: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val showHotSign: Boolean,
    val showImmediately: Boolean,
    val slogan: Any,
    val src: Int,
    val startTime: Long,
    val subTitle: String,
    val subtitles: List<Any>,
    val switchStatus: Boolean,
    val tags: List<Tag>,
    val text: String,
    val thumbPlayUrl: Any,
    val title: String,
    val titleList: List<String>,
    val titlePgc: Any,
    val topicTitle: String,
    val type: String,
    val uid: Int,
    val userCover: String,
    val url: String,
    val urls: List<String>?,
    val user: User,
    val videoPosterBean: VideoPosterBean,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: WebUrl
) : Serializable

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
) : Serializable

data class Banner(
    val background_image: String,
    val link: String,
    val poster_image: String,
    val tag_name: String,
    val title: String
) : Serializable

data class Consumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
) : Serializable


data class Header(
    val actionUrl: String,
    val cover: Any,
    val description: String,
    val font: Any,
    val icon: String,
    val iconType: String,
    val id: Int,
    val issuerName: String,
    val label: Any,
    val labelList: Any,
    val rightText: String,
    val showHateVideo: Boolean,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val time: Long,
    val title: String
) : Serializable {
    companion object {
        //圆形icon
        const val ICON_TYPE_ROUND = "round"

        //方形icon
        const val ICON_TYPE_SQUARE = "square"
    }
}


data class Follow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
) : Serializable

data class Shield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
) : Serializable


data class Cover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String
) : Serializable

data class PlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<Url>,
    val width: Int
) : Serializable

data class Provider(
    val alias: String,
    val icon: String,
    val name: String
) : Serializable

data class Tag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: Any,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: Any,
    val tagRecType: String
) : Serializable

data class VideoPosterBean(
    val fileSizeStr: Any,
    val scale: Any,
    val url: Any
) : Serializable

data class WebUrl(
    val forWeibo: String,
    val raw: String
) : Serializable


data class Url(
    val name: String,
    val size: Int,
    val url: String
) : Serializable

data class User(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Any,
    val city: Any,
    val country: Any,
    val cover: Any,
    val description: Any,
    val expert: Boolean,
    val followed: Boolean,
    val gender: Any,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Any,
    val uid: Int,
    val university: Any,
    val userType: String
)


data class Detail(
    val actionUrl: String,
    val adTrack: List<AdTrack>,
    val adaptiveImageUrls: String,
    val adaptiveUrls: String,
    val canSkip: Boolean,
    val categoryId: Int,
    val countdown: Boolean,
    val cycleCount: Int,
    val description: String,
    val displayCount: Int,
    val displayTimeDuration: Int,
    val icon: String,
    val id: Int,
    val ifLinkage: Boolean,
    val imageUrl: String,
    val iosActionUrl: String,
    val linkageAdId: Int,
    val loadingMode: Int,
    val openSound: Boolean,
    val position: Int,
    val showActionButton: Boolean,
    val showImage: Boolean,
    val showImageTime: Int,
    val timeBeforeSkip: Int,
    val title: String,
    val url: String,
    val videoAdType: String,
    val videoType: String
) : Serializable

data class AdTrack(
    val clickUrl: String,
    val monitorPositions: String,
    val needExtraParams: List<String>,
    val organization: String,
    val playUrl: String,
    val viewUrl: String
) : Serializable