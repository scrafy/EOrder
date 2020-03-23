package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.LandingViewModel
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel


class LandingActivity : BaseMenuActivity()  {


    private lateinit var model: LandingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        model = getViewModel()
        setListeners()
        setMenuToolbar()
    }

   /* override fun checkToken() {

        if (!model.isValidToken()){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }*/


    private fun setListeners() {


        findViewById<TextView>(R.id.textView_order).setOnClickListener { view ->

            startActivity(Intent(this, OrderActivity::class.java))

        }
    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

}
