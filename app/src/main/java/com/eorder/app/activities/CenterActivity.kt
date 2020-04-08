package com.eorder.app.activities

import android.os.Bundle
import android.widget.LinearLayout
import com.eorder.app.R
import com.eorder.app.com.eorder.app.viewmodels.CenterActivityViewModel
import com.eorder.app.fragments.CenterInfoFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.interfaces.ISelectCenter
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

        var fragment = CenterInfoFragment()
        var args = Bundle()
        args.putSerializable("center", center as Serializable )
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linearLayout_activity_center_container, fragment)
            .addToBackStack(null).commit()
    }


    private fun init() {

        var t = findViewById<LinearLayout>(R.id.linearLayout_activity_center_container)

        supportFragmentManager.beginTransaction()
            .add(R.id.linearLayout_activity_center_container, CentersFragment()).commit()

    }
}
