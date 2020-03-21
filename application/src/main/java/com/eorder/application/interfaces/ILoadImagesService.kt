package com.eorder.application.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.models.UrlLoadedImage


interface ILoadImagesService {

    fun loadImages(list: List<UrlLoadedImage>) : LiveData<List<UrlLoadedImage>>
    fun returnsloadImageErrorObservable(): LiveData<Throwable>


}
