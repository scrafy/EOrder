package com.eorder.application.services


import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.extensions.toBase64
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.models.UrlLoadedImage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL


class LoadImagesService : ILoadImagesService {

    private val resultImageBase64Observable: MutableLiveData<List<UrlLoadedImage>> =
        MutableLiveData()
    private val errorLoadImageObservable: MutableLiveData<Throwable> = MutableLiveData()

    override fun loadImages(list: List<UrlLoadedImage>): LiveData<List<UrlLoadedImage>> {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            list.filter { item -> item.imageUrl != null }.forEach { item ->

                try{
                    item.imageBase64 =  Picasso.get().load(item.imageUrl).get().toBase64()
                }
                catch(ex: Exception){

                }

            }
            resultImageBase64Observable.postValue(list)
        }
        return resultImageBase64Observable
    }

    override fun returnsloadImageErrorObservable(): LiveData<Throwable> {
        return errorLoadImageObservable
    }

    private fun handleError(): CoroutineExceptionHandler {

        return CoroutineExceptionHandler { _, ex ->
            errorLoadImageObservable.postValue(ex)
        }
    }
}