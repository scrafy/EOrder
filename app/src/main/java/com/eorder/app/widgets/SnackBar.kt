package com.eorder.app.widgets

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.eorder.app.R
import com.google.android.material.snackbar.Snackbar


@RequiresApi(Build.VERSION_CODES.M)
class SnackBar(

    private val context: Context,
    private val v: View,
    private val button_text: String,
    private val message: String,
    private val duration: Int = Snackbar.LENGTH_INDEFINITE

) {

    fun show() {

        val snackbar = Snackbar.make(
            v,
            message,
            duration
        )
        snackbar.setActionTextColor(context.resources.getColor(R.color.colorAccent, null))
        snackbar.setAction(button_text) {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}