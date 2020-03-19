package com.eorder.app.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IRepaintShopIcon
import com.eorder.app.com.eorder.app.interfaces.ISelectCatalog
import com.eorder.app.com.eorder.app.interfaces.ISelectSeller
import com.eorder.app.com.eorder.app.viewmodels.OrderViewModel
import com.eorder.app.fragments.CatalogsFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment
import com.eorder.app.fragments.SellersFragment
import com.eorder.app.interfaces.ISelectCenter
import com.eorder.app.interfaces.IToolbarSearch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class OrderActivity : BaseMenuActivity(), ISelectCenter, ISelectCatalog, IRepaintShopIcon, IToolbarSearch, ISelectSeller {

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

    override fun getSearchFromToolbar(search: String) {

        (this.supportFragmentManager.fragments.first() as IToolbarSearch).getSearchFromToolbar(search)
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

        var fragment = SellersFragment()
        var args = Bundle()

        args.putInt("centerId", centerId)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()

        model.addCenterToOrder(centerId)
    }

    override fun selectSeller(sellerId: Int) {

        var fragment = CatalogsFragment()
        var args = Bundle()

        args.putInt("sellerId", sellerId)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()

        model.addSellerToOrder(sellerId)
    }

    override fun selectCatalog(catalogId: Int) {

        var args = Bundle()
        var fragment = ProductsFragment()
        args.putInt("catalogId", catalogId)
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
