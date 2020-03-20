package com.eorder.app.com.eorder.app.dialogs


import androidx.appcompat.app.AlertDialog

abstract class Dialog {

    protected lateinit var dialog: AlertDialog

    abstract fun show()
}