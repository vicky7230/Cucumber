package com.vicky7230.cucumber.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.vicky7230.cucumber.data.network.model.recipes.Recipe


/**
 * Created by vicky on 3/1/18.
 */

class RecipeDiffUtilCallback(
    private val oldRecipeList: MutableList<Recipe>?,
    private val newRecipeList: MutableList<Recipe>?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldRecipeList?.size!!
    }

    override fun getNewListSize(): Int {
        return newRecipeList?.size!!
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipeList?.get(oldItemPosition)?.recipeId == newRecipeList?.get(newItemPosition)?.recipeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipeList?.get(oldItemPosition) == newRecipeList?.get(newItemPosition)
    }
}