package com.eorder.app.viewmodels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.interfaces.IManageException
import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.interfaces.IGetCatalogsBySellerUseCase
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.*

class CatalogsViewModel(
    private val getCatalogBySellerUseCase: IGetCatalogsBySellerUseCase,
    val manageExceptionService: IManageException,
    private val loadImageService: ILoadImagesService
) : BaseViewModel() {

    private val getCatalogBySellersResult: MutableLiveData<ServerResponse<List<Catalog>>> =
        MutableLiveData()


    fun getCatalogBySellersObservable(): LiveData<ServerResponse<List<Catalog>>> =
        getCatalogBySellersResult

    fun getCatalogBySeller(sellerId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = getCatalogBySellerUseCase.getrCatalogsBySeller(sellerId)
            getCatalogBySellersResult.postValue(result)
        }
    }

    fun loadImages(list:List<UrlLoadedImage>) = loadImageService.loadImages(list)
    fun getLoadImageErrorObservable() = loadImageService.returnsloadImageErrorObservable()
}
