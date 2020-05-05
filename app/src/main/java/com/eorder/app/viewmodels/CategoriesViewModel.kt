package com.eorder.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class CategoriesViewModel : BaseMainMenuActionsViewModel() {

    val categoriesResult: MutableLiveData<ServerResponse<List<Category>>> = MutableLiveData()


    fun getCategories(catalogId:Int) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {

            var result = unitOfWorkUseCase.getCategoriesUseCase().getCategories(catalogId)
            categoriesResult.postValue(result)
        }
    }

}
