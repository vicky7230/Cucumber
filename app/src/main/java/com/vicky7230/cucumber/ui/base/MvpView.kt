package com.vicky7230.cucumber.ui.base

/**
 * Created by vicky on 11/2/18.
 */
interface MvpView {

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)

    fun showError(message: String)
}