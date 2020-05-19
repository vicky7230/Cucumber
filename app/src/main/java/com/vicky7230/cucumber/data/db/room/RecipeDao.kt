package com.vicky7230.cayenne.data.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import io.reactivex.Flowable
import androidx.room.Delete


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