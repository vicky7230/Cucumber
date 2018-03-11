package com.vicky7230.cucumber.data.network.model.recipes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Recipes(
    @SerializedName("count")
    @Expose
    var count: Int? = null,
    @SerializedName("recipes")
    @Expose
    var recipes: MutableList<Recipe>? = null
)

