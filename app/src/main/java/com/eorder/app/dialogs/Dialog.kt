package com.eorder.app.com.eorder.app.dialogs

import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class Dialog {

    protected lateinit var dialog: AlertDialog

    abstract fun setListeners(view: View)
    abstract fun show()
}