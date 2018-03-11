package com.vicky7230.cucumber.data.network.model.singleRecipe

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Recipe {

    @SerializedName("publisher")
    @Expose
    var publisher: String? = null
    @SerializedName("f2f_url")
    @Expose
    var f2fUrl: String? = null
    @SerializedName("ingredients")
    @Expose
    var ingredients: List<String>? = null
    @SerializedName("source_url")
    @Expose
    var sourceUrl: String? = null
    @SerializedName("recipe_id")
    @Expose
    var recipeId: String? = null
    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null
    @SerializedName("social_rank")
    @Expose
    var socialRank: Float? = null
    @SerializedName("publisher_url")
    @Expose
    var publisherUrl: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null

}
