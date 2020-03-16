package com.eorder.app.activities

import android.content.Intent
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IGetSearchObservable
import com.eorder.app.com.eorder.app.interfaces.ISetActionBar


abstract class MenuActivity : AppCompatActivity(), IGetSearchObservable, ISetActionBar {


    protected var currentToolBarMenu: MutableMap<String, Int> = mutableMapOf()
    private val search: MutableLiveData<String> = MutableLiveData()


    override fun getSearchObservable() : LiveData<String> {
        return search
    }

    override fun setActionBar(menu: MutableMap<String, Int>) {
        currentToolBarMenu = menu
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)
    }


    protected fun setToolbarAndLateralMenu(menu: MutableMap<String, Int>) {

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        if (menu.values.first() == R.menu.main_menu)
            toolbar.setPadding(0, 0, 10, 0)
        if (menu.values.first() == R.menu.product_list_menu)
            toolbar.setPadding(0, 0, 70, 0)
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
                        getContext().search.postValue(query ?: "") //= query ?: ""
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        getContext().search.postValue(newText ?: "")
                         return true
                    }

                })

            }

        }

        return super.onCreateOptionsMenu(menu)
    }


    protected fun getContext() = this
}
