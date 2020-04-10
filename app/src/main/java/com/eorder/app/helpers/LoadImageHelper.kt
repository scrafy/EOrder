package com.eorder.app.helpers

import android.content.Context
import android.widget.ImageView
import com.eorder.app.R
import com.eorder.application.di.UnitOfWorkService
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
            loadImageService?.loadImage(
                img,
                GifDrawable(context?.resources!!, R.drawable.loading),
                url,
                isCircle
            )
        else {
            setGifLoading(img)
        }

    }

    private fun setGifLoading(img: ImageView) {
        try {
            img.setImageDrawable(GifDrawable(context?.resources!!, R.drawable.loading))
        } catch (ex: Exception) {

        }
    }
}