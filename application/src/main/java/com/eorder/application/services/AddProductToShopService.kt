package com.eorder.application.services

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.R
import com.eorder.application.interfaces.IAddProductToShopService
import com.eorder.application.interfaces.IShopService
import com.eorder.application.interfaces.IUserCentersUseCase
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
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
            AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.product_to_shop_service_product))
                .setMessage(context.resources.getString(R.string.product_to_shop_service_product_added))
                .setPositiveButton("OK") { d, i ->
                    d.dismiss()
                }.create().show()

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

            AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.product_to_shop_service_product))
                .setMessage(context.resources.getString(R.string.product_to_shop_service_product_exists))
                .setPositiveButton("OK") { d, i ->
                    d.dismiss()
                }.create().show()
            return true
        }
        return false
    }

    private fun checkSeller(context: Context, product: Product) {

        if (shopService.getOrder().seller.sellerId != product.sellerId) {
            AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.product_to_shop_service_product))
                .setMessage(R.string.product_to_shop_service_product_different_seller)
                .setNegativeButton(context.resources.getString(R.string.no)) { d, i ->
                    d.dismiss()
                }
                .setPositiveButton(context.resources.getString(R.string.yes)) { d, i ->
                    shopService.cleanShop()
                    addProductToShop(context, product)
                }.create().show()
        } else {
            product.amount++
            shopService.addProductToShop(product)
            AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.product_to_shop_service_product))
                .setMessage(context.resources.getString(R.string.product_to_shop_service_product_added))
                .setPositiveButton("OK") { d, i ->
                    d.dismiss()
                }.create().show()
        }
    }

}