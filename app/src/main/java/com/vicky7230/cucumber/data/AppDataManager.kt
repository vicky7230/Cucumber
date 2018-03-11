package com.vicky7230.cucumber.data

import com.vicky7230.cucumber.data.db.AppDbHelper
import com.vicky7230.cucumber.data.network.AppApiHelper
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import javax.inject.Inject

/**
 * Created by vicky on 31/12/17.
 */
class AppDataManager @Inject
constructor(private val appApiHelper: AppApiHelper, private val appDbHelper: AppDbHelper) :
    DataManager {
    override fun getRecipes(key: String, page: String, count: String) =
        appApiHelper.getRecipes(key, page, count)

    override fun getRecipe(key: String, rId: String) =
        appApiHelper.getRecipe(key, rId)

    override fun searchRecipes(key: String, query: String, page: String) =
        appApiHelper.searchRecipes(key, query, page)

    override fun insertRecipe(recipe: Recipe?) =
        appDbHelper.insertRecipe(recipe)

    override fun selectRecipes() =
        appDbHelper.selectRecipes()

    override fun selectRecipe(recipeId: String) =
        appDbHelper.selectRecipe(recipeId)

    override fun deleteRecipe(recipe: Recipe?) {
        appDbHelper.deleteRecipe(recipe)
    }
}
