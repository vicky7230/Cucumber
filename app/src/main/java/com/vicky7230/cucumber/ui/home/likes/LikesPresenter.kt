package com.vicky7230.cucumber.ui.home.likes

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
 * Created by vicky on 17/2/18.
 */
class LikesPresenter<V : LikesMvpView> @Inject constructor(
    private val dataManager: DataManager,
    private val compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable), LikesMvpPresenter<V> {

    override fun getRecipes() {

        compositeDisposable.addAll(
            dataManager.selectRecipes()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recipes ->
                    if (!isViewAttached())
                        return@subscribe
                    if (recipes != null) {
                        mvpView?.updateLikeList(recipes)
                    }
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.showMessage(throwable.message!!)
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

    override fun removeRecipe(recipe: Recipe) {

        compositeDisposable.addAll(
            Observable.defer { Observable.just(dataManager.deleteRecipe(recipe)) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.showMessage("Recipe Removed.")
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.hideLoading()
                    mvpView?.showMessage(throwable.message!!)
                    Timber.e(throwable.message)
                })
        )
    }
}