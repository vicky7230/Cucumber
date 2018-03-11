package com.vicky7230.cucumber.ui.base

import com.vicky7230.cucumber.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by vicky on 11/2/18.
 */
open class BasePresenter<V : MvpView> @Inject constructor(
    private val dataManager: DataManager,
    private val compositeDisposable: CompositeDisposable
) : MvpPresenter<V> {

    var mvpView: V? = null

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        this.mvpView = null
    }

    fun isViewAttached(): Boolean {
        return mvpView != null
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
        RuntimeException("Please call Presenter.onAttach(MvpView) before" + " requesting data to the Presenter")
}