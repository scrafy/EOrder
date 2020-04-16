package com.eorder.application.services

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.extensions.toBase64
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.domain.interfaces.ILoadImageFields
import com.eorder.application.models.UrlLoadedImage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent


class LoadImagesService : KoinComponent, ILoadImagesService {

    private var picasso: Picasso =
        Picasso.Builder(getKoin().rootScope.androidContext()).build()


    private val resultImageBase64Observable: MutableLiveData<List<UrlLoadedImage>> =
        MutableLiveData()

    private val resultImageObservable: MutableLiveData<Any> =
        MutableLiveData()


    override fun loadImages(list: List<UrlLoadedImage>): LiveData<List<UrlLoadedImage>> {


        CoroutineScope(Dispatchers.IO).launch {

            list.filter { item -> item.imageUrl != null }.forEach { item ->

                try {
                    item.imageBase64 = picasso.load(item.imageUrl).get().toBase64()
                } catch (ex: Exception) {

                }
            }
            resultImageBase64Observable.postValue(list)
        }
        return resultImageBase64Observable
    }

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

    override fun loadImage(obj: ILoadImageFields): LiveData<Any> {

        var bitMap: Bitmap? = null
        val picasso: Picasso =
            Picasso.Builder(getKoin().rootScope.androidContext()).build()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                if (obj.imageUrl == null)
                    resultImageObservable.postValue(null)
                else {
                    bitMap = picasso.load(obj.imageUrl).get()
                    obj.image = bitMap as Bitmap
                    resultImageObservable.postValue(obj)
                }

            } catch (ex: Exception) {
                bitMap = null
                resultImageObservable.postValue(null)
            }

        }
        return resultImageObservable

    }

}