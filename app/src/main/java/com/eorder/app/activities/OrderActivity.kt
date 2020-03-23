package com.eorder.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.eorder.app.R
import com.eorder.app.com.eorder.app.dialogs.AlertDialogQuestion
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
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import com.eorder.domain.models.Seller
import org.koin.androidx.viewmodel.ext.android.getViewModel


class OrderActivity : BaseMenuActivity(), ISelectCenter, ISelectCatalog, IRepaintShopIcon,
    IToolbarSearch, ISelectSeller {

    private lateinit var model: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        model = getViewModel()
        init()
        setMenuToolbar()
    }

   /* override fun checkToken() {
        if (!model.isValidToken()) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }*/

    override fun repaintShopIcon() {

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        if (model.getProductsFromShop().isNotEmpty()) {

            val menuItem = toolbar?.menu?.findItem(R.id.item_menu_product_list_shop)
            menuItem?.setIcon(R.drawable.ic_orange_full_order)
        } else {
            val menuItem = toolbar?.menu?.findItem(R.id.item_menu_product_list_shop)
            menuItem?.setIcon(R.drawable.ic_shopping_cart_white_24dp)
        }
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun getSearchFromToolbar(search: String) {

        (this.supportFragmentManager.fragments.first() as IToolbarSearch).getSearchFromToolbar(
            search
        )
    }

    override fun setMenuToolbar() {

        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        super.onCreateOptionsMenu(menu)
        if (menu?.findItem(R.id.item_menu_product_list_shop) != null) {
            this.repaintShopIcon()
        }
        return true
    }

    override fun selectCenter(center: Center) {

        if (model.isPossibleChangeCenter(center)) {

            loadSellersFragment(center)

        } else {
            AlertDialogQuestion(
                this,
                "Shop",
                getString(R.string.alert_dialog_order_activity_change_center).format(model.getCurrentOrderCenter().center_name,center.center_name),
                resources.getString(R.string.alert_dialog_order_activity_change_center_button_confirm),
                resources.getString(R.string.alert_dialog_order_activity_button_deny),
                { d, i ->

                    model.cleanShop()
                    loadSellersFragment(center)

                },
                { d, i ->


                }).show()
        }

    }

    override fun selectSeller(seller: Seller) {

        if (model.isPossibleChangeSeller(seller)) {

            loadCatalogsFragment(seller)

        } else {
            AlertDialogQuestion(
                this,
                "Shop",
                 getString(R.string.alert_dialog_order_activity_change_seller).format(model.getCurrentOrderSeller().companyName,seller.companyName),
                resources.getString(R.string.alert_dialog_order_activity_change_seller_button_confirm),
                resources.getString(R.string.alert_dialog_order_activity_button_deny),
                { d, i ->

                    model.cleanShop()
                    loadCatalogsFragment(seller)

                },
                { d, i ->


                }).show()
        }

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

    private fun loadSellersFragment(center: Center) {

        var fragment = SellersFragment()
        var args = Bundle()
        args.putInt("centerId", center.id!!)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()

        model.addCenterToOrder(center)
    }

    private fun loadCatalogsFragment(seller: Seller) {

        var fragment = CatalogsFragment()
        var args = Bundle()

        args.putInt("sellerId", seller.id!!)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()

        model.addSellerToOrder(seller)
    }

}
