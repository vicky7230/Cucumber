package com.vicky7230.cucumber.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.vicky7230.cucumber.R


/**
 * Created by vicky on 11/2/18.
 */
object CommonUtils {

    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }
}