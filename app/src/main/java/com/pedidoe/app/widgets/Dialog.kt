package com.pedidoe.app.widgets


import androidx.appcompat.app.AlertDialog

abstract class Dialog {

    protected lateinit var dialog: AlertDialog

    abstract fun show()
}