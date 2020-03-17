package com.eorder.app.interfaces

import androidx.lifecycle.LiveData

interface IGetSearchObservable {

    fun getSearchObservable() : LiveData<String>

}