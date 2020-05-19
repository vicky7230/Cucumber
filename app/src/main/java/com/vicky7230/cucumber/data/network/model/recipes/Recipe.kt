package com.vicky7230.cucumber.data.network.model.recipes


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipes")
data class Recipe(

    @Ignore
    var type: String = "RECIPE",

    @SerializedName("publisher")
    @Expose
    @ColumnInfo(name = "publisher")
    var publisher: String = "",

    @SerializedName("f2f_url")
    @Expose
    @ColumnInfo(name = "f2f_url")
    var f2fUrl: String = "",

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("source_url")
    @Expose
    @ColumnInfo(name = "source_url")
    var sourceUrl: String = "",

    @SerializedName("recipe_id")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "recipe_id")
    var recipeId: String = "",

    @SerializedName("image_url")
    @Expose
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",

    @SerializedName("social_rank")
    @Expose
    @ColumnInfo(name = "social_rank")
    var socialRank: Float = 0f,

    @SerializedName("publisher_url")
    @Expose
    @ColumnInfo(name = "publisher_url")
    var publisherUrl: String = "",

    @ColumnInfo(name = "liked")
    var liked: Boolean = false
)
