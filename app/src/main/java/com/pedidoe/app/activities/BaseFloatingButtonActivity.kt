package com.pedidoe.app.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.pedidoe.domain.models.Product
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

    @SuppressLint("RestrictedApi")
    fun hideFloatingButton() {
        cart_floating_button.visibility = View.INVISIBLE

    }

    override fun onStart() {
        this.showFloatingButton()
        setListeners()
        super.onStart()
    }

    private fun setListeners() {

        cart_floating_button.setOnClickListener {

            onFloatingButtonClicked()
        }
    }

    private fun onFloatingButtonClicked() {
        var intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
    }

}