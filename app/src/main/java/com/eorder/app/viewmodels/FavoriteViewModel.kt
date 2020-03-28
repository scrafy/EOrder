import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.viewmodels.BaseMainMenuActionsViewModel
import com.eorder.application.interfaces.IGetFavoriteProductsUseCase
import com.eorder.application.interfaces.ILoadImagesService
import com.eorder.application.interfaces.ISharedPreferencesService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IManageException
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel(

    private val shopService: IShopService,
    private val sharedPreferencesService: ISharedPreferencesService,
    jwtTokenService: IJwtTokenService,
    manageExceptionService: IManageException,
    private val loadImagesService: ILoadImagesService,
    private val getFavoriteProductsUseCase: IGetFavoriteProductsUseCase

) : BaseMainMenuActionsViewModel(
    jwtTokenService,
    sharedPreferencesService,
    manageExceptionService
) {


    private val favoriteProductsResult: MutableLiveData<ServerResponse<List<Product>>> =
        MutableLiveData()


    fun getfavoriteProductsResultObservable(): LiveData<ServerResponse<List<Product>>> =
        favoriteProductsResult


    fun getProductsFromShop() = shopService.getOrder().products
    fun loadImages(list: List<UrlLoadedImage>) = loadImagesService.loadImages(list)
    fun removeProductFromFavorites(context: Context, productId: Int) {
        var list = sharedPreferencesService.loadFromSharedPreferences<MutableList<Int>>(
            context,
            "favorite_products",
            mutableListOf<Int>()::class.java
        ) ?: return
        list = list.map { p -> p.toInt() }.toMutableList()
        list.remove(productId)
        sharedPreferencesService.writeToSharedPreferences(
            context,
            list,
            "favorite_products",
            list::class.java
        )
    }

    fun loadFavoriteProducts(context: Context) {

        CoroutineScope(Dispatchers.IO).launch(this.handleError()) {
            favoriteProductsResult.postValue(getFavoriteProductsUseCase.getFavoriteProducts(context))
        }

    }
}