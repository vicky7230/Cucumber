package com.vicky7230.cucumber.ui.home.recipes

import com.vicky7230.cucumber.data.Config
import com.vicky7230.cucumber.data.DataManager
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by vicky on 13/2/18.
 */
class RecipesPresenter<V : RecipesMvpView> @Inject constructor(
    private val dataManager: DataManager,
    private val compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable), RecipesMvpPresenter<V> {

    private var page = 1

    override fun getRecipes() {

        compositeDisposable.addAll(
            dataManager.getRecipes(
                Config.API_KEY,
                page.toString(),
                "10"
            )
                .map { recipes ->
                    if (recipes.recipes != null) {
                        for (recipe in recipes.recipes!!) {
                            recipe.liked = dataManager.selectRecipe(recipe.recipeId).size > 0
                        }
                    }
                    recipes
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recipes ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.hideLoading()
                    if (recipes.recipes != null) {
                        mvpView?.updateRecipeList(recipes.recipes!!)
                        ++page
                    }
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.hideLoading()
                    mvpView?.showMessage(throwable.message!!)
                    mvpView?.showErrorInRecyclerView()
                    Timber.e(throwable.message)
                })
        )
    }

    override fun getRecipe(recipeId: String) {

        mvpView?.showLoading()
        compositeDisposable.addAll(
            dataManager.getRecipe(
                Config.API_KEY,
                recipeId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ singleRecipe ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.hideLoading()
                    if (singleRecipe.recipe != null) {
                        mvpView?.showIngredients(singleRecipe.recipe?.ingredients)
                    }
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.hideLoading()
                    mvpView?.showMessage(throwable.message!!)
                    Timber.e(throwable.message)
                })
        )
    }

    override fun saveRecipe(recipe: Recipe?) {
        compositeDisposable.addAll(
            Observable.defer { Observable.just(dataManager.insertRecipe(recipe)) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.showMessage("Recipe Liked.")
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.showMessage(throwable.message!!)
                    Timber.e(throwable.message)
                })
        )
    }
}