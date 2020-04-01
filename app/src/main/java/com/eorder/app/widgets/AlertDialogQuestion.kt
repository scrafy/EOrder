package com.eorder.app.widgets

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class AlertDialogQuestion
    (

    ctx: Context,
    title: String,
    message: String,
    buttonTextPositive: String,
    buttonTextNegative: String,
    callbackPositive: (dialog: DialogInterface, which: Int) -> Unit,
    callbackNegative: (dialog: DialogInterface, which: Int) -> Unit

) : Dialog() {

    init {

        this.dialog = AlertDialog.Builder(ctx)

            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonTextPositive, callbackPositive)
            .setNegativeButton(buttonTextNegative, callbackNegative)
            .create()
    }

    override fun show() {
        this.dialog.show()
    }
}