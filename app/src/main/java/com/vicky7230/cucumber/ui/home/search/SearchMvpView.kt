package com.vicky7230.cucumber.ui.home.search

import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.MvpView

/**
 * Created by vicky on 17/2/18.
 */
interface SearchMvpView : MvpView {
    fun refreshSearchList(recipes: MutableList<Recipe>)
    fun updateRecipeList(recipes: MutableList<Recipe>)
    fun showErrorInRecyclerView()
}