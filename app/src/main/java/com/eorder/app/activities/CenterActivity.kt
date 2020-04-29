package com.eorder.app.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.eorder.app.R
import com.eorder.app.fragments.CenterInfoFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.interfaces.ISelectCenter
import com.eorder.app.viewmodels.CenterActivityViewModel
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.io.Serializable


class CenterActivity : BaseMenuActivity(), ISelectCenter {

    private lateinit var model: CenterActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)
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

    override fun selectCenter(center: Center) {

        CenterInfoFragment.center = center

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linearLayout_activity_center_container, CenterInfoFragment())
            .addToBackStack(null).commit()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {


        val fragment = CentersFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.linearLayout_activity_center_container, fragment).commit()


    }

}
