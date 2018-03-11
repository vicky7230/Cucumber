package com.vicky7230.cucumber.data.network

import javax.inject.Inject

/**
 * Created by vicky on 31/12/17.
 */
class AppApiHelper @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun getRecipe(key: String, rId: String) =
        apiService.getRecipe(key, rId)

    override fun getRecipes(key: String, page: String, count: String) =
        apiService.getRecipes(key, page, count)

    override fun searchRecipes(key: String, query: String, page: String) =
        apiService.searchRecipes(key, query, page)
}