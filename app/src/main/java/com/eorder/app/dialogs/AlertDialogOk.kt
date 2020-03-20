package com.eorder.app.dialogs

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.eorder.app.R
import com.eorder.app.com.eorder.app.dialogs.Dialog


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