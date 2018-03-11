package com.vicky7230.cucumber.ui.home.recipes

import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.MvpView

/**
 * Created by vicky on 13/2/18.
 */
interface RecipesMvpView : MvpView {
    fun updateRecipeList(recipes: MutableList<Recipe>)
    fun showIngredients(ingredients: List<String>?)
    fun showErrorInRecyclerView()
}