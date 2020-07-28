package com.pedidoe.app.viewmodels.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.pedidoe.app.viewmodels.BaseMainMenuActionsViewModel
import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class CategoriesViewModel : BaseMainMenuActionsViewModel() {

    val categoriesResult: MutableLiveData<ServerResponse<List<Category>>> = MutableLiveData()



    fun getCategories(catalogId: Int, centreId: Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCategoriesUseCase().getCategories(catalogId, centreId)
            categoriesResult.postValue(result)
        }
    }

    fun getCenterSelected() = unitOfWorkService.getShopService().getOrder().center.centerId

}
