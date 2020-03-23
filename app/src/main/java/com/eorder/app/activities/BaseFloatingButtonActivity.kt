package com.eorder.app.com.eorder.app.activities

import android.content.Intent
import android.view.View
import com.eorder.app.activities.ShopActivity
import com.eorder.domain.models.Product
import kotlinx.android.synthetic.main.activity_landing.*

abstract class BaseFloatingButtonActivity : BaseActivity() {

    abstract fun getProductsFromShop(): List<Product>

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

        cart_floating_button.setOnClickListener { v ->

            startActivity(Intent(this, ShopActivity::class.java))
        }
    }

    fun hideFloatingButton() {
        cart_floating_button.visibility = View.INVISIBLE
    }

}