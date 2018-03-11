package com.vicky7230.cayenne.data.db.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import io.reactivex.Flowable
import android.arch.persistence.room.Delete


/**
 * Created by vicky on 31/12/17.
 */
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe?): Long

    @Query("SELECT * FROM recipes")
    fun selectRecipes(): Flowable<MutableList<Recipe>>

    @Query("SELECT * FROM recipes WHERE recipe_id LIKE :recipeId")
    fun selectRecipe(recipeId: String): MutableList<Recipe>

    @Delete
    fun deleteRecipe(recipe: Recipe?)
}