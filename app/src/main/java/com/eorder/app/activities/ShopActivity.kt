package com.eorder.app.activities

import android.os.Bundle
import android.widget.Toast
import com.eorder.app.R


class ShopActivity : MenuActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"destroy shop", Toast.LENGTH_LONG).show()
    }

    private fun init(){
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }
}
