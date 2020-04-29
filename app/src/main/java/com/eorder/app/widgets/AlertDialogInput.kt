package com.eorder.app.widgets

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.eorder.app.R


class AlertDialogInput(

    ctx: Context,
    title: String,
    message: String,
    buttonTextPositive: String,
    buttonTextNegative: String,
    callbackPositive: (dialog: DialogInterface, which: Int) -> Unit,
    callbackNegative: (dialog: DialogInterface, which: Int) -> Unit

) : Dialog() {

    var input: EditText

    init {

        val layout = LayoutInflater.from(ctx).inflate(R.layout.dialog_input, null)

        input = layout.findViewById(R.id.editText_dialog_input_units)
        input.requestFocus()

        this.dialog = AlertDialog.Builder(ctx)

            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonTextPositive, callbackPositive)
            .setNegativeButton(buttonTextNegative, callbackNegative)
            .setView(layout)
            .create()
    }

    override fun show() {
        this.dialog.show()
    }
}