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

        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        cardView_activity_landing_make_order.setOnClickListener {

            intent.setClass(this, OrderActivity::class.java)
            startActivity(intent)

        }

        cardView_activity_landing_favorites.setOnClickListener {


            intent.setClass(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        cardView_activity_landing_orders.setOnClickListener {

            intent.setClass(this, OrderDoneActivity::class.java)
            startActivity(intent)

        }

        cardView_activity_landing_catalogs.setOnClickListener {

            intent.setClass(this, ProductActivity::class.java)
            startActivity(intent)

        }

        cardView_activity_landing_centers.setOnClickListener {

            intent.setClass(this, CenterActivity::class.java)
            startActivity(intent)
        }

        cardView_activity_landing_sellers.setOnClickListener {

            intent.setClass(this, SellerActivity::class.java)
            startActivity(intent)
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
