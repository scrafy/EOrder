package com.eorder.app.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.eorder.app.R
import com.eorder.app.fragments.SellerInfoFragment
import com.eorder.app.fragments.SellersFragment
import com.eorder.app.interfaces.ISelectSeller
import com.eorder.app.viewmodels.SellerActivityViewModel
import com.eorder.domain.models.Product
import com.eorder.domain.models.Seller
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class SellerActivity : BaseMenuActivity(), ISelectSeller {

    private lateinit var model: SellerActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)
        model = getViewModel()
        setMenuToolbar()
        init()
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

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun selectSeller(seller: Seller) {

        SellerInfoFragment.seller = seller

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linearLayout_activity_seller_container, SellerInfoFragment())
            .addToBackStack(null).commit()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {

        supportFragmentManager.beginTransaction()
            .add(R.id.linearLayout_activity_seller_container, SellersFragment()).commit()

    }
}
