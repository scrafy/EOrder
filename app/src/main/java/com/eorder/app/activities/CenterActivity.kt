package com.eorder.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.*
import com.eorder.app.fragments.CatalogsByCenterFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment


class CenterActivity : BaseActivity(), IShowCatalogsByCenter, IShowCenterInfo,
    IShowProductsByCatalog {

    override fun addActionBar(menu: MutableMap<String, Int>) {

        currentToolBarMenu = menu
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centers)
        init()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun showCatalogsByCenter(centerId: Int?) {

        if (centerId != null) {

            var args = Bundle()
            args.putInt("centerId", centerId)
            var fragment = CatalogsByCenterFragment()
            fragment.arguments = args
            supportFragmentManager.beginTransaction()
                .replace(R.id.linear_layout_center_fragment_container, fragment)
                .addToBackStack(null).commit()

        } else {
            //TODO show snack bar informando de que el centro id es invalido
        }
    }

    override fun showProductsByCatalog(catalogId: Int?) {

        if (catalogId != null) {

            var args = Bundle()
            args.putInt("catalogId", catalogId)
            var fragment = ProductsFragment()
            fragment.arguments = args
            supportFragmentManager.beginTransaction()
                .replace(R.id.linear_layout_center_fragment_container, fragment)
                .addToBackStack(null).commit()

        } else {
            //TODO show snack bar informando de que el centro id es invalido
        }
    }


    override fun showCenterInfo(centerId: Int?) {
        Toast.makeText(this, centerId.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun init() {

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, CentersFragment()).commit()
    }
}
