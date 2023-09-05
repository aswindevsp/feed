package com.storm.feedv2.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.storm.feedv2.model.Comment
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.joinToString(",") // Convert the list to a comma-separated string
    }

    @TypeConverter
    fun toList(value: String?): List<Int> {
        if (value.isNullOrEmpty()) {
            return emptyList() // Return an empty list if the input string is null or empty
        }
        return try {
            value.split(",").map { it.toInt() } // Split the string and convert to integers
        } catch (e: NumberFormatException) {
            // Handle the NumberFormatException by returning an empty list
            emptyList()
        }
    }

    @TypeConverter
    fun fromCommentList(commentList: List<Comment>?): String {
        if (commentList == null) {
            return ""
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Comment>>() {}.type
        return gson.toJson(commentList, type)
    }

    @TypeConverter
    fun toCommentList(commentListString: String): List<Comment> {
        if (commentListString.isEmpty()) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(commentListString, type)
    }
}
