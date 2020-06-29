package com.pedidoe.application.services

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedidoe.application.interfaces.ILoadImagesService
import com.pedidoe.domain.interfaces.ILoadImageFields
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent


class LoadImagesService : KoinComponent, ILoadImagesService {


    private val resultImageObservable: MutableLiveData<Any> =
        MutableLiveData()


    override fun loadImage(img: ImageView, default: Drawable?, url: String, isCircle: Boolean) {

        var bitMap: Bitmap? = null
        val mainThreadHandler = Handler(Looper.getMainLooper())
        val picasso: Picasso =
            Picasso.Builder(getKoin().rootScope.androidContext()).build()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                bitMap = picasso.load(url).get()
            } catch (ex: Exception) {
                bitMap = null
            }

            mainThreadHandler.post {

                if (bitMap != null) {

                    if (isCircle) {

                        val roundedBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(
                                getKoin().rootScope.androidContext().resources,
                                bitMap
                            )
                        roundedBitmapDrawable.isCircular = true
                        img.setImageDrawable(roundedBitmapDrawable)
                    } else {
                        img.setImageBitmap(bitMap)
                    }
                } else {
                    img.setImageDrawable(default)
                }
            }

        }

    }

    override fun loadImage(list: List<ILoadImageFields>): LiveData<Any> {

        var bitMap: Bitmap
        val picasso: Picasso =
            Picasso.Builder(getKoin().rootScope.androidContext()).build()

        if ( list.isEmpty())
            return resultImageObservable

        CoroutineScope(Dispatchers.IO).launch {

            list.filter { p -> p.imageUrl != null }.forEach { p ->

                try {
                    bitMap = picasso.load(p.imageUrl).get()
                    p.image = bitMap

                } catch (ex: Exception) {
                    p.image = null
                }
            }
            resultImageObservable.postValue(null)
        }
        return resultImageObservable

    }

    override fun loadImage(obj: ILoadImageFields): LiveData<Any> {

        var bitMap: Bitmap
        val picasso: Picasso =
            Picasso.Builder(getKoin().rootScope.androidContext()).build()


        CoroutineScope(Dispatchers.IO).launch {

            try {
                bitMap = picasso.load(obj.imageUrl).get()
                obj.image = bitMap

            } catch (ex: Exception) {
                obj.image = null
            }
            resultImageObservable.postValue(null)
        }
        return resultImageObservable

    }

}