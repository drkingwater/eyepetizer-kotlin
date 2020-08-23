package me.pxq.common.retrofit

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import me.pxq.common.model.Cover

/**
 * Description: Gson Adapter for [Cover]；返回的json数据中key为cover的值有两种类型，需要自定义解析
 * Author : pxq
 * Date : 2020/8/14 11:39 PM
 */
class CoverTypeAdapter : TypeAdapter<Cover>() {
    override fun write(writer: JsonWriter?, value: Cover?) {
        writer ?: return
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.blurred)
            writer.value(value.detail)
            writer.value(value.feed)
            writer.value(value.homepage)
        }
    }

    override fun read(reader: JsonReader?): Cover {
        reader ?: return Cover("", "", "", "")
        var token = reader.peek()
        // 是cover 字符串
        if (token == JsonToken.STRING) {
            val coverUrl = reader.nextString()
            return Cover("", coverUrl, "", coverUrl)
        } else {  // 是cover 对象
            var blurred = ""
            var detail = ""
            var feed = ""
            var homepage = ""
            // 开始解析Cover对象
            reader.beginObject()
            while (reader.peek().also {
                    token = it
                } != JsonToken.END_OBJECT) {
                // 忽略NULL
                if (token == JsonToken.NULL) {
                    // 跳过NULL
                    reader.nextNull()
                    continue
                }
                // 读取json name
                reader.nextName()?.apply {
                    var value = ""
                    // 只读取String类型,忽略其他，比如NULL
                    if (reader.peek() == JsonToken.STRING) {
                        value = reader.nextString()
                    }
                    // 赋值
                    when (this) {
                        "blurred" -> blurred = value
                        "detail" -> detail = value
                        "feed" -> feed = value
                        "homepage" -> homepage = value
                        else -> Unit
                    }
                }
            }
            reader.endObject()
            return Cover(blurred, detail, feed, homepage)
        }
    }

}