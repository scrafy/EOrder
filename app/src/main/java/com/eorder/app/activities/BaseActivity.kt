package com.eorder.app.activities

import android.content.Intent
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.eorder.app.R


abstract class BaseActivity : AppCompatActivity() {


    protected var currentToolBarMenu: MutableMap<String, Int> = mutableMapOf()


    override fun onStart() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        setToolbarAndLateralMenu(currentToolBarMenu)
        when(currentToolBarMenu.keys.first()){

            "product_list_menu" -> {

                val itemShop = menu?.findItem(R.id.item_menu_product_list_shop)
                itemShop?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener{


                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        startActivity(Intent(getContext(), ShopActivity::class.java))
                        return true
                    }

                })
                val itemSearch = menu?.findItem(R.id.item_menu_product_list_search)
                val search = (itemSearch?.actionView as SearchView)
                search.queryHint = resources.getString(R.string.toolbar_proudct_list_search)
                search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                       /* var search = (ProductsFragment.self as IToolBarSearch)
                        search.search(newText ?: "")*/
                        return true
                    }

                })
            }

        }

        return super.onCreateOptionsMenu(menu)
    }

    abstract fun addActionBar(menu: MutableMap<String, Int>)


    private fun setListenersMainMenu() {

        this.findViewById<Toolbar>(R.id.toolbar)
            .setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {

                    when (item?.itemId) {

                        R.id.profile -> Toast.makeText(
                            getContext(),
                            "Profile",
                            Toast.LENGTH_LONG
                        ).show()
                        R.id.setting -> Toast.makeText(
                            getContext(),
                            "Settings",
                            Toast.LENGTH_LONG
                        ).show()
                        R.id.signout -> Toast.makeText(
                            getContext(),
                            "Salir",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return true
                }


            })
    }

    private fun setToolbarAndLateralMenu(menu: Map<String, Int>) {

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        if (toolbar.id == R.menu.main_menu)
            toolbar.setPadding(0, 0, 10, 0)
        if (toolbar.id == R.menu.product_list_menu)
            toolbar.setPadding(0, 0, 20, 0)
        toolbar.inflateMenu(menu.values.first())
        setListenersMainMenu()
        val drawerLayout = this.findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout)
        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.lateral_nav_menu_open,
            R.string.lateral_nav_menu_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    fun getContext() = this
}
