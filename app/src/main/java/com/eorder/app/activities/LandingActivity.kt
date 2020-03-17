package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.eorder.app.R


class LandingActivity : BaseMenuActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setListeners()
        init()
        setMenuToolbar()

    }


    private fun setListeners() {

        findViewById<TextView>(R.id.textView_center).setOnClickListener{view ->

            startActivity(Intent(this, CenterActivity::class.java))

        }
    }

    private fun init(){
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

}
