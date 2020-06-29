package com.pedidoe.app.widgets

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.pedidoe.app.R


import android.view.inputmethod.InputMethodManager


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
        val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        this.dialog = AlertDialog.Builder(ctx)

            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonTextPositive){i,v ->
                imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                callbackPositive(i,v)}
            .setNegativeButton(buttonTextNegative){i,v ->
                imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                callbackNegative(i,v)
            }
            .setView(layout)
            .create()


    }

    override fun show() {
        this.dialog.show()
    }
}