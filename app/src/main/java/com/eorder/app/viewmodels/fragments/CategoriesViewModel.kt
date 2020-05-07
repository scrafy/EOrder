package com.eorder.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.domain.models.Category
import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class CategoriesViewModel : BaseMainMenuActionsViewModel() {

    val categoriesResult: MutableLiveData<ServerResponse<List<Category>>> = MutableLiveData()
    val productsResult: MutableLiveData<ServerResponse<List<Product>>> = MutableLiveData()


    fun getCategories(catalogId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCategoriesUseCase().getCategories(catalogId)
            categoriesResult.postValue(result)
        }
    }

    fun getProducts(search: SearchProduct) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getSearchProductsUseCase().searchProducts(search)
            productsResult.postValue(result)
        }
    }

    fun getCenterSelected() = unitOfWorkService.getShopService().getOrder().center.centerId

}
