package com.vicky7230.cucumber.ui.home.likes

import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.MvpView

/**
 * Created by vicky on 17/2/18.
 */
interface LikesMvpView : MvpView {
    fun updateLikeList(recipes: MutableList<Recipe>?)
    fun showIngredients(ingredients: List<String>?)
}