package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import com.eorder.app.R
import com.eorder.app.viewmodels.LandingViewModel
import com.eorder.domain.models.Product
import kotlinx.android.synthetic.main.activity_landing.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class LandingActivity : BaseMenuActivity() {

    private lateinit var model: LandingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        model = getViewModel()
        setListeners()
        setMenuToolbar()

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
            startActivity(Intent(this, SellerProductActivity::class.java))
        }

        textView_center.setOnClickListener{
            startActivity(Intent(this, CenterActivity::class.java))
        }

        textView_seller.setOnClickListener{
            startActivity(Intent(this, SellerActivity::class.java))
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
