package com.eorder.app.widgets

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class AlertDialogOk
    (

    ctx: Context,
    title: String,
    message: String,
    buttonText: String,
    callback: (dialog: DialogInterface, which: Int) -> Unit

) : Dialog() {

    init {

        this.dialog = AlertDialog.Builder(ctx)

            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText, callback)
            .create()
    }

    override fun show() {
        this.dialog.show()
    }

}