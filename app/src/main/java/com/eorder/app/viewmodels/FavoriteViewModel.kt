import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.application.enums.SharedPreferenceKeyEnum
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class FavoriteViewModel: BaseMainMenuActionsViewModel() {

    private val favoriteProductsResult: MutableLiveData<ServerResponse<List<Product>>> =
        MutableLiveData()


    fun getfavoriteProductsResultObservable(): LiveData<ServerResponse<List<Product>>> =
        favoriteProductsResult


    fun getProductsFromShop() = unitOfWorkService.getShopService().getOrder().products
    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)

    fun removeProductFromFavorites(context: Context, productId: Int) {
        var list =
            unitOfWorkService.getSharedPreferencesService().loadFromSharedPreferences<MutableList<Int>>(
                context,
                SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
                mutableListOf<Int>()::class.java
            ) ?: return
        list = list.map { p -> p.toInt() }.toMutableList()
        list.remove(productId)
        unitOfWorkService.getSharedPreferencesService().writeToSharedPreferences(
            context,
            list,
            SharedPreferenceKeyEnum.FAVORITE_PRODUCTS.key,
            list::class.java
        )
    }

    fun loadFavoriteProducts(context: Context) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            favoriteProductsResult.postValue(
                unitOfWorkUseCase.getFavoriteProductsUseCase().getFavoriteProducts(
                    context
                )
            )
        }

    }
}