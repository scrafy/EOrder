package com.eorder.app.widgets

import android.content.Context
import android.view.View
import com.eorder.app.R
import com.google.android.material.snackbar.Snackbar

class SnackBar(

    private val context: Context,
    private val v: View,
    private val button_text: String,
    private val message: String

) {

    fun show() {

        val snackbar = Snackbar.make(
            v,
            message,
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setActionTextColor(context.resources.getColor(R.color.colorAccent, null))
        snackbar.setAction(button_text) {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}