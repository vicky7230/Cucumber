package com.vicky7230.cucumber.ui.base


/**
 * Created by vicky on 11/2/18.
 */
interface MvpPresenter<V : MvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()
}