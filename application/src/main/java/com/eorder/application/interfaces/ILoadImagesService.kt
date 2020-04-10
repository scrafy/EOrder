package com.eorder.application.interfaces

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.eorder.application.models.UrlLoadedImage


interface ILoadImagesService {

    fun loadImages(list: List<UrlLoadedImage>) : LiveData<List<UrlLoadedImage>>
    fun loadImage(img: ImageView, default: Drawable, url:String, isCircle:Boolean)
}
