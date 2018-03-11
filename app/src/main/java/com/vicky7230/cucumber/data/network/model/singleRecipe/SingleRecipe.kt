package com.vicky7230.cucumber.data.network.model.singleRecipe

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SingleRecipe {

    @SerializedName("recipe")
    @Expose
    var recipe: Recipe? = null

}
