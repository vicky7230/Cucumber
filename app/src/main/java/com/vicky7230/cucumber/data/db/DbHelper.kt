package com.vicky7230.cucumber.data.db

import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import io.reactivex.Flowable

/**
 * Created by vicky on 31/12/17.
 */
interface DbHelper {

    fun insertRecipe(recipe: Recipe?): Long

    fun selectRecipes(): Flowable<MutableList<Recipe>>

    fun selectRecipe(recipeId: String): MutableList<Recipe>

    fun deleteRecipe(recipe: Recipe?)
}