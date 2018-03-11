package com.vicky7230.cucumber.ui.home.search

import com.vicky7230.cucumber.data.Config
import com.vicky7230.cucumber.data.DataManager
import com.vicky7230.cucumber.data.network.model.recipes.Recipe
import com.vicky7230.cucumber.data.network.model.recipes.Recipes
import com.vicky7230.cucumber.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by vicky on 17/2/18.
 */
class SearchPresenter<V : SearchMvpView> @Inject constructor(
    private val dataManager: DataManager,
    private val compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable), SearchMvpPresenter<V> {
    var page = 1

    var query = ""
    override fun search(subject: PublishSubject<String>) {
        compositeDisposable.add(
            subject
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(Predicate { it: String ->
                    return@Predicate it.isNotEmpty()
                })
                .distinctUntilChanged()
                .switchMap(Function<String, ObservableSource<Recipes>> { it ->
                    query = it
                    return@Function dataManager.searchRecipes(
                        Config.API_KEY,
                        it,
                        "1"
                    ).map { recipes ->
                        if (recipes.recipes != null) {
                            for (recipe in recipes.recipes!!) {
                                recipe.liked = dataManager.selectRecipe(recipe.recipeId).size > 0
                            }
                        }
                        recipes
                    }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                })
                .retry()
                .subscribe({ recipes ->
                    if (!isViewAttached())
                        return@subscribe
                    if (recipes.recipes != null) {
                        mvpView?.refreshSearchList(recipes.recipes!!)
                        page = 2
                    }
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.showMessage(throwable.message!!)
                    Timber.e(throwable.message)
                })
        )
    }

    override fun getNextPage() {
        compositeDisposable.add(
            dataManager.searchRecipes(
                Config.API_KEY,
                query,
                page.toString()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recipes ->
                    if (!isViewAttached())
                        return@subscribe
                    if (recipes.recipes != null) {
                        mvpView?.updateRecipeList(recipes.recipes!!)
                        ++page
                    }
                }, { throwable ->
                    if (!isViewAttached())
                        return@subscribe
                    mvpView?.showMessage(throwable.message!!)
                    mvpView?.showErrorInRecyclerView()
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