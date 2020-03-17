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

    private val layout: Int?,
    private val ctx: Context,
    private val title: String,
    private val message: String,
    private val buttonText: String,
    private val callback: (dialog: DialogInterface, which: Int) -> Unit

) : Dialog() {

    init {

        if (layout == null) {

            this.dialog = AlertDialog.Builder(ctx)

                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, callback)
                .create()

        } else {

            val view = LayoutInflater.from(ctx).inflate(layout, null)
            this.dialog = AlertDialog.Builder(ctx)
                .setView(view)
                .create()

            setListeners(view)

        }
    }

    override fun show() {
        this.dialog?.show()
    }

    override fun setListeners(view: View) {

        view.findViewById<TextView>(R.id.alert_dialog_message)?.text = message
        view.findViewById<TextView>(R.id.alert_dialog_title)?.text = title
        view.findViewById<TextView>(R.id.alert_dialog_ok)?.text = buttonText
        view.findViewById<TextView>(R.id.alert_dialog_ok)?.setOnClickListener { v ->

            callback(this.dialog, 0)
        }
    }

}