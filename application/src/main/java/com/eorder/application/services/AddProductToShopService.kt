package com.eorder.application.services

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.application.R
import com.eorder.application.interfaces.IAddProductToShopService
import com.eorder.application.interfaces.IShopService
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product



class AddProductToShopService(
    private val shopService: IShopService
) : IAddProductToShopService {

    private val productAdded: MutableLiveData<Any> = MutableLiveData()


    override fun getproductAddedObservable(): LiveData<Any> = productAdded

    override fun addProductToShop(context: Context, product: Product, center:Center) {

        if (shopService.isShopEmpty()) {
            addProduct(context, product, center)
        } else {

            if (!isProductRepeated(context, product)) {
                checkSeller(context, product, center)
            }

        }
    }

    private fun addProduct(context: Context, product: Product, center:Center) {

        val order = shopService.getOrder()
        order.center.centerId = center.id
        order.center.centerName = center.center_name
        order.center.imageUrl = center.imageUrl
        order.seller.sellerId = product.sellerId
        order.seller.sellerName = product.sellerName
        product.amount++
        shopService.addProductToShop(product)
        productAdded.value = null
        showOkDialog(
            context,
            context.resources.getString(R.string.product_to_shop_service_product),
            context.resources.getString(R.string.product_to_shop_service_product_added)
        )

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

    private fun checkSeller(context: Context, product: Product, center:Center) {

        if (shopService.getOrder().seller.sellerId != product.sellerId) {
            showQuestionDialog(
                context,
                context.resources.getString(R.string.product_to_shop_service_product),
                context.resources.getString(R.string.product_to_shop_service_product_different_seller)

            ) { d, i ->
                shopService.cleanShop()
                addProductToShop(context, product, center)
            }
        } else {
            checkCenter(context, product, center)
        }
    }

    private fun checkCenter(context: Context, product: Product, center: Center) {

        if (shopService.getOrder().center.centerId != center.id) {
            showQuestionDialog(
                context,
                context.resources.getString(R.string.product_to_shop_service_product),
                context.resources.getString(R.string.product_to_shop_service_product_different_center)

            ) { d, i ->
                shopService.cleanShop()
                addProductToShop(context, product, center)
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
            .setCancelable(false)
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
            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(context.resources.getString(R.string.no)) { d, i ->
                d.dismiss()
            }
            .setPositiveButton(context.resources.getString(R.string.yes), positiveCallback).create()
            .show()
    }

}