package com.eorder.application.services

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.R
import com.eorder.application.interfaces.IAddProductToShopService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.interfaces.IUserCentersUseCase
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


class AddProductToShopService(
    private val shopService: IShopService,
    private val userCentersUseCase: IUserCentersUseCase
) : IAddProductToShopService {


    private val productAdded: MutableLiveData<Any> = MutableLiveData()


    override fun getproductAddedObservable(): LiveData<Any> = productAdded

    override fun addProductToShop(context: Context, product: Product) {

        if (shopService.isShopEmpty()) {
            addProduct(context, product)
        } else {

            if (!isProductRepeated(context, product)) {
                checkSeller(context, product)
            }

        }
    }

    private fun addProduct(context: Context, product: Product) {

        val centers = getUserCenters()!!
        val builder = AlertDialog.Builder(context)

        builder.setTitle(context.resources.getString(R.string.product_to_shop_service_choose_center))
        builder.setSingleChoiceItems(
            centers.map { c -> c.center_name }.toTypedArray(),
            -1
        ) { d, i ->

            val order = shopService.getOrder()
            order.center.centerId = centers[i].id
            order.center.centerName = centers[i].center_name
            order.center.centerImageUrl = centers[i].imageUrl
            order.seller.sellerId = product.sellerId
            order.seller.sellerName = product.sellerName
            product.amount++
            shopService.addProductToShop(product)
            productAdded.value = null
            d.dismiss()
            showOkDialog(
                context,
                context.resources.getString(R.string.product_to_shop_service_product),
                context.resources.getString(R.string.product_to_shop_service_product_added)
            )

        }.show()

    }

    private fun getUserCenters(): List<Center>? {

        var centers: List<Center>? = null
        runBlocking(Dispatchers.IO) {
            centers = userCentersUseCase.getCenters().serverData?.data
        }
        return centers
    }

    private fun isProductRepeated(context: Context, product: Product): Boolean {

        if (shopService.getOrder().products.firstOrNull { p -> p.id == product.id } != null) {
            showOkDialog(
                context,
                context.resources.getString(R.string.product_to_shop_service_product),
                context.resources.getString(R.string.product_to_shop_service_product_exists)
            )
            return true
        }
        return false
    }

    private fun checkSeller(context: Context, product: Product) {

        if (shopService.getOrder().seller.sellerId != product.sellerId) {
            showQuestionDialog(
                context,
                context.resources.getString(R.string.product_to_shop_service_product),
                context.resources.getString(R.string.product_to_shop_service_product_different_seller)

            ) { d, i ->
                shopService.cleanShop()
                addProductToShop(context, product)
            }
        } else {
            product.amount++
            shopService.addProductToShop(product)
            showOkDialog(
                context,
                context.resources.getString(R.string.product_to_shop_service_product),
                context.resources.getString(R.string.product_to_shop_service_product_added)
            )
        }
    }

    private fun showOkDialog(context: Context, title: String, message: String) {

        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { d, i ->
                d.dismiss()
            }.create().show()
    }

    private fun showQuestionDialog(
        context: Context,
        title: String,
        message: String,
        positiveCallback: (DialogInterface, Int) -> Unit
    ) {

        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(context.resources.getString(R.string.no)) { d, i ->
                d.dismiss()
            }
            .setPositiveButton(context.resources.getString(R.string.yes), positiveCallback).create()
            .show()
    }

}