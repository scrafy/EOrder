package com.eorder.app.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.adapters.fragments.CentersAdapter
import com.eorder.app.com.eorder.app.interfaces.IShowCatalogsByCenter
import com.eorder.app.com.eorder.app.interfaces.IShowCenterInfo
import com.eorder.app.com.eorder.app.interfaces.IShowProductsByCatalog
import com.eorder.app.fragments.CatalogsByCenterFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment


class CenterActivity : AppCompatActivity(), IShowCatalogsByCenter, IShowCenterInfo, IShowProductsByCatalog {

    var recyclerView : RecyclerView ? = null
    var adapter = CentersAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centers)
        init()
    }

    override fun showCatalogsByCenter(centerId: Int?) {

        if (centerId != null){

            var args = Bundle()
            args.putInt("centerId", centerId)
            var fragment = CatalogsByCenterFragment()
            fragment.arguments = args
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout_center_fragment_container, fragment).addToBackStack(null).commit()

        }else{
            //TODO show snack bar informando de que el centro id es invalido
        }
    }

    override fun showProductsByCatalog(catalogId: Int?) {

        if (catalogId != null){

            var args = Bundle()
            args.putInt("catalogId", catalogId)
            var fragment = ProductsFragment()
            fragment.arguments = args
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout_center_fragment_container, fragment).addToBackStack(null).commit()

        }else{
            //TODO show snack bar informando de que el centro id es invalido
        }
    }


    override fun showCenterInfo(centerId: Int?){
        Toast.makeText(this, centerId.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun init() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.lateral_nav_menu_open, R.string.lateral_nav_menu_close)
        var layout = LinearLayoutManager(this)

        toolbar.inflateMenu(R.menu.main_menu)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportFragmentManager.beginTransaction().add(R.id.linear_layout_center_fragment_container, CentersFragment()).commit()

    }
}
