package com.eorder.app.activities

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.interfaces.IToolbarSearch
import com.eorder.app.interfaces.ISetActionBar
import com.eorder.application.interfaces.IShopService
import com.eorder.infrastructure.interfaces.IJwtTokenService
import org.koin.android.ext.android.inject


abstract class BaseMenuActivity : BaseFloatingButtonActivity(), ISetActionBar {


    protected var currentToolBarMenu: MutableMap<String, Int> = mutableMapOf()


    abstract fun setMenuToolbar()


    override fun setActionBar(menu: MutableMap<String, Int>) {
        currentToolBarMenu = menu
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        setToolbarAndLateralMenu(currentToolBarMenu)
        when (currentToolBarMenu.keys.first()) {

            "cart_menu" -> {

                val itemShop = menu?.findItem(R.id.item_menu_product_list_shop)
                itemShop?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {


                    override fun onMenuItemClick(item: MenuItem?): Boolean {

                        val shopService by inject<IShopService>()

                        if (shopService.order.products.isEmpty()) {

                            AlertDialogOk(
                                getContext(),
                                resources.getString(R.string.alert_dialog_shop_empty_title),
                                resources.getString(R.string.alert_dialog_shop_empty_message),
                                "OK"
                            ) { dialog, i ->

                                dialog.cancel()
                            }.show()

                            return true
                        }

                        startActivity(Intent(getContext(), ShopActivity::class.java))
                        return true
                    }

                })
                val itemSearch = menu?.findItem(R.id.item_menu_product_list_search)
                val search = (itemSearch?.actionView as SearchView)
                search.queryHint = resources.getString(R.string.toolbar_proudct_list_search)
                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        (getContext() as IToolbarSearch).getSearchFromToolbar(query ?: "")
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        (getContext() as IToolbarSearch).getSearchFromToolbar(newText ?: "")
                        return true
                    }

                })

            }

        }

        return super.onCreateOptionsMenu(menu)
    }


    protected fun setToolbarAndLateralMenu(menu: MutableMap<String, Int>) {

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        if (menu.values.first() == R.menu.main_menu)
            toolbar.setPadding(0, 0, 10, 0)
        if (menu.values.first() == R.menu.cart_menu)
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
                        R.id.signout -> {
                            getContext().tokenService.token = null
                            startActivity(Intent(getContext(), MainActivity::class.java))
                        }

                    }
                    return true
                }


            })
    }

    protected fun getContext() = this
}
