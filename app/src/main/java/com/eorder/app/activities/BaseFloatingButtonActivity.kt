package com.eorder.app.activities

import android.annotation.SuppressLint
import android.view.View
import com.eorder.app.interfaces.IOnFloatinButtonShopClicked
import com.eorder.domain.models.Product
import kotlinx.android.synthetic.main.activity_landing.*

abstract class BaseFloatingButtonActivity : BaseActivity() {

    abstract fun getProductsFromShop(): List<Product>

    @SuppressLint("RestrictedApi")
    fun showFloatingButton() {
        if (this.getProductsFromShop().isEmpty()) {

            cart_floating_button.visibility = View.INVISIBLE
        } else {
            cart_floating_button.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        this.showFloatingButton()
        setListeners()
        super.onStart()
    }

    private fun setListeners() {

        cart_floating_button.setOnClickListener {

            (this as IOnFloatinButtonShopClicked).onFloatingButtonClicked()

        }
    }

    @SuppressLint("RestrictedApi")
    fun hideFloatingButton() {
        cart_floating_button.visibility = View.INVISIBLE

    }

}