package com.eorder.app.activities


import android.os.Bundle
import com.eorder.app.R
import com.eorder.app.fragments.CentersFragment



class CenterActivity : BaseMenuActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centers)
        init()
        setMenuToolbar()

    }

    private fun init() {

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, CentersFragment()).commit()


    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }
}
