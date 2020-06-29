package com.pedidoe.application.interfaces

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.pedidoe.domain.interfaces.ILoadImageFields


interface ILoadImagesService {

    fun loadImage(img: ImageView, default: Drawable?, url:String, isCircle:Boolean)
    fun loadImage(list: List<ILoadImageFields>): LiveData<Any>
    fun loadImage(obj: ILoadImageFields): LiveData<Any>

}
