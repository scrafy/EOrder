package com.pedidoe.app.helpers

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.LiveData
import com.pedidoe.app.R
import com.pedidoe.application.di.UnitOfWorkService
import com.pedidoe.domain.interfaces.ILoadImageFields
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import pl.droidsonroids.gif.GifDrawable


class LoadImageHelper : KoinComponent {

    private val loadImageService = inject<UnitOfWorkService>().value.getLoadImageService()
    private val context: Context = getKoin().rootScope.androidContext()


    fun loadImage(
        url: String?,
        img: ImageView,
        isCircle: Boolean
    ) {

        if (url != null)
            loadImageService.loadImage(
                img,
                GifDrawable(context.resources!!, R.drawable.loading),
                url,
                isCircle
            )
        else {
            setGifLoading(img)
        }

    }

    fun loadImage(
        obj: List<ILoadImageFields>
    ): LiveData<Any> {


        return loadImageService.loadImage(obj)

    }

    fun loadImage(
        obj: ILoadImageFields
    ): LiveData<Any> {


        return loadImageService.loadImage(obj)

    }


    fun setGifLoading(img: ImageView, gif:Int = R.drawable.loading) {
        try {
            img.setImageDrawable(GifDrawable(context.resources!!, gif))
        } catch (ex: Exception) {

        }
    }

    fun setImageAsCircle(img: ImageView, bitMap: Bitmap) {

        val roundedBitmapDrawable =
            RoundedBitmapDrawableFactory.create(
                getKoin().rootScope.androidContext().resources,
                bitMap
            )
        roundedBitmapDrawable.isCircular = true
        img.setImageDrawable(roundedBitmapDrawable)
    }
}