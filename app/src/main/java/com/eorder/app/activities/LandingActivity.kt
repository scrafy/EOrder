package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import com.eorder.app.R
import com.eorder.app.interfaces.IOnFloatinButtonShopClicked
import com.eorder.app.viewmodels.LandingViewModel
import com.eorder.domain.models.Product
import kotlinx.android.synthetic.main.activity_landing.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class LandingActivity : BaseMenuActivity(), IOnFloatinButtonShopClicked {

    private lateinit var model: LandingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        model = getViewModel()
        setListeners()
        setMenuToolbar()

    }

    override fun onFloatingButtonClicked() {
        var intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
    }

    private fun setListeners() {


        textView_landing_activity_make_order.setOnClickListener {

            startActivity(Intent(this, OrderActivity::class.java))
        }

        textView_landing_page_favorite_products.setOnClickListener {

            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        textView_landing_activity_orders_done.setOnClickListener {

            startActivity(Intent(this, OrderDoneActivity::class.java))
        }

        textView_product.setOnClickListener{
            startActivity(Intent(this, ProductActivity::class.java))
        }
    }

    override fun onBackPressed() {

    }

    override fun checkValidSession() {

        model.checkValidSession(this)
    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun signOutApp() {
        model.signOutApp(this)
    }

}
