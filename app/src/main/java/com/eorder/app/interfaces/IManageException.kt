package com.eorder.app.interfaces

import android.content.Context


interface IManageException {

    fun manageException(context: Context, ex: Throwable)
}