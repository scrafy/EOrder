package com.eorder.app.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IRepaintShopIcon
import com.eorder.app.com.eorder.app.interfaces.ISelectCatalog
import com.eorder.app.com.eorder.app.viewmodels.OrderViewModel
import com.eorder.app.fragments.CatalogsByCenterFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment
import com.eorder.app.interfaces.ISelectCenter
import org.koin.androidx.viewmodel.ext.android.getViewModel


class OrderActivity : BaseMenuActivity(), ISelectCenter, ISelectCatalog, IRepaintShopIcon {

    private lateinit var model: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        model = getViewModel()
        init()
        setMenuToolbar()
    }

    override fun repaintShopIcon() {

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        if ( !model.getProductsFromShop().isEmpty() ) {

            val menuItem = toolbar?.menu?.findItem(R.id.item_menu_product_list_shop)
            menuItem?.setIcon(R.drawable.ic_white_full_order)
        } else {
            val menuItem = toolbar?.menu?.findItem(R.id.item_menu_product_list_shop)
            menuItem?.setIcon(R.drawable.ic_shopping_cart_white_24dp)
        }
    }

    override fun setMenuToolbar() {

        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        super.onCreateOptionsMenu(menu)
        if ( menu?.findItem(R.id.item_menu_product_list_shop) != null ){
            this.repaintShopIcon()
        }
        return true
    }

    override fun selectCenter(centerId: Int) {

        var args = Bundle()
        args.putInt("centerId", centerId)
        var fragment = CatalogsByCenterFragment()
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    override fun selectCatalog(catalogId: Int) {

        var args = Bundle()
        args.putInt("catalogId", catalogId)
        var fragment = ProductsFragment()
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    private fun init() {

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, CentersFragment()).commit()
    }

}
