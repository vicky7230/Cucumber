package com.vicky7230.cucumber.ui.base

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.vicky7230.cucumber.utils.CommonUtils


/**
 * Created by vicky on 11/2/18.
 */
open class BaseActivity : AppCompatActivity(), MvpView {

    private var progressDialog: Dialog? = null

    fun hasPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true
        return permissions.none {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog!!.cancel()
        }
    }

    override fun showMessage(message: String) {
        if (message != null)
            displayMessage(message)
    }

    override fun showError(message: String) {
        if (message != null)
            displayError(message)
    }

}