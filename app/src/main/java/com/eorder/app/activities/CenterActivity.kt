package com.eorder.app.activities

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.*
import com.eorder.app.fragments.CatalogsByCenterFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment


class CenterActivity : AppCompatActivity(), IShowCatalogsByCenter, IShowCenterInfo, IShowProductsByCatalog,
    IChangeToolbar {

    private lateinit var currentToolbarMenu: Map<String, Int>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centers)
        init()
        setToolBar(R.menu.main_menu)
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

    override fun changeToolbar(menuItems: Map<String, Int>) {

        currentToolbarMenu = menuItems
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(currentToolbarMenu.values.first(), menu)
        setToolBar(currentToolbarMenu.values.first())
        when(currentToolbarMenu.keys.first()){

            "product_list_menu" -> {

                val item = menu?.findItem(R.id.item_menu_product_list_search)
                val search = (item?.actionView as SearchView)
                search.queryHint = resources.getString(R.string.toolbar_proudct_list_search)
                search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        var search = (ProductsFragment.self as IToolBarSearch)
                        search.search(newText ?: "")
                        return true
                    }

                })
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun init() {

        supportFragmentManager.beginTransaction().add(R.id.linear_layout_center_fragment_container, CentersFragment()).commit()
    }

    private fun setToolBar(menuId: Int){

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.lateral_nav_menu_open, R.string.lateral_nav_menu_close)


        toolbar.inflateMenu(menuId)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }
}
