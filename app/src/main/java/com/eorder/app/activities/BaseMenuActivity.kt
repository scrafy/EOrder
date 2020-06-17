package com.eorder.app.activities

import android.content.Intent
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.eorder.app.R
import com.eorder.app.interfaces.IFavoriteIconClicked
import com.eorder.app.interfaces.ISetActionBar
import com.eorder.app.interfaces.IToolbarSearch
import com.google.android.material.navigation.NavigationView


abstract class BaseMenuActivity : BaseFloatingButtonActivity(), ISetActionBar {


    abstract fun setMenuToolbar()
    abstract fun signOutApp()

    protected var currentToolBarMenu: MutableMap<String, Int> = mutableMapOf()
    protected fun getContext() = this
    private var showLateralMenu: Boolean = true


    override fun setActionBar(
        menu: MutableMap<String, Int>,
        showBack: Boolean,
        showLateralMenu: Boolean
    ) {
        this.showLateralMenu = showLateralMenu
        currentToolBarMenu = menu
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)
        if (showBack)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return super.onSupportNavigateUp()

    }

    override fun onStart() {
        super.onStart()
        bindEventsToLateralMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        setToolbarAndLateralMenu(currentToolBarMenu)
        when (currentToolBarMenu.keys.first()) {

            "search_menu" -> {

                val itemFavorite = menu?.findItem(R.id.item_menu_product_list_favorite)

                itemFavorite!!.setOnMenuItemClickListener {

                    (getContext() as IFavoriteIconClicked).onFavoriteIconClicked()
                     true
                }

                val itemSearch = menu ?. findItem (R.id.item_menu_product_list_search)
                val search = (itemSearch?.actionView as SearchView)
                search.queryHint = resources.getString(R.string.toolbar_proudct_list_search)
                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        search.isIconified = true
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


    protected fun setToolbarAndLateralMenu(
        menu: MutableMap<String, Int>

    ) {

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        if (menu.values.first() == R.menu.main_menu) {
            setListenersMainMenu()
            toolbar.setPadding(0, 0, 10, 0)
        }

        toolbar.inflateMenu(menu.values.first())
        if (this.showLateralMenu) {

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
    }

    private fun setListenersMainMenu() {

        this.findViewById<Toolbar>(R.id.toolbar)
            .setOnMenuItemClickListener { item ->
                when (item?.itemId) {

                    R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
                    /* R.id.setting -> Toast.makeText(
                         getContext(),
                         "Settings",
                         Toast.LENGTH_LONG
                     ).show()*/
                    R.id.signout -> {
                        getContext().signOutApp()
                    }

                }
                true
            }
    }

    private fun bindEventsToLateralMenu() {

        if (this.findViewById<NavigationView>(R.id.navView_naview) != null) {

            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            this.findViewById<NavigationView>(R.id.navView_naview)
                .setNavigationItemSelectedListener {

                    this.findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout)
                        .closeDrawer(Gravity.LEFT)

                    when (it.itemId) {

                        R.id.makeorder -> {
                            intent.setClass(this, OrderActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.centers -> {
                            intent.setClass(this, CenterActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.sellers -> {
                            intent.setClass(this, SellerActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.orders_done -> {
                            intent.setClass(this, OrderDoneActivity::class.java)
                            startActivity(intent)
                        }
                        /*R.id.favourite_products -> {
                            intent.setClass(this, FavoriteActivity::class.java)
                            startActivity(intent)
                        }*/
                        R.id.profile -> {
                            intent.setClass(this, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.products -> {
                            intent.setClass(this, ProductActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    true
                }
        }


    }

}
