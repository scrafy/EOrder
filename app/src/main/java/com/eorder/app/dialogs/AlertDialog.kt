package com.eorder.app.com.eorder.app.dialogs

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class AlertDialog(val ctx: Context, val title: String, val message: String, val buttonText: String, val callback: (dialog: DialogInterface, which: Int) -> Unit) {

    private var dialog: AlertDialog? = null

    init{

        this.dialog =  AlertDialog.Builder(ctx)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText, callback)
            .create()
    }

    fun show(){
         this.dialog?.show()
    }

}