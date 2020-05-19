package com.vicky7230.cucumber.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vicky7230.cayenne.data.db.room.RecipeDao
import com.vicky7230.cucumber.data.network.model.recipes.Recipe

/**
 * Created by vicky on 31/12/17.
 */
@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}