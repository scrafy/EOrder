package com.eorder.app.activities


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.eorder.app.R
import kotlinx.android.synthetic.main.toolbar.*


class LandingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        init()
        setListeners()
    }

    fun setListeners() {

        findViewById<TextView>(R.id.textView_center).setOnClickListener{view ->

            startActivity(Intent(this, CenterActivity::class.java))

        }
    }

    private fun init() {

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.main_menu)


        val drawerLayout = findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.lateral_nav_menu_open, R.string.lateral_nav_menu_close)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }
}
