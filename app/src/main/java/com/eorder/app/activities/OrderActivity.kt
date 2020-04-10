package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.eorder.app.R
import com.eorder.app.fragments.CatalogsFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment
import com.eorder.app.interfaces.*
import com.eorder.app.viewmodels.OrderViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class OrderActivity : BaseMenuActivity(), ISelectCenter, ISelectCatalog, IRepaintShopIcon,
    IShowSnackBarMessage, IToolbarSearch, IOnShopToolbarIconClicked {

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

    override fun onShopIconClicked() {

        if (model.getProductsFromShop().isEmpty()) {

            AlertDialogOk(
                getContext(),
                resources.getString(R.string.alert_dialog_shop_empty_title),
                resources.getString(R.string.alert_dialog_shop_empty_message),
                "OK"
            ) { dialog, i ->

                dialog.cancel()
            }.show()

            return
        }
        var intent = Intent(this, ShopActivity::class.java)
        intent.putExtra(
            "activity_name",
            "order"
        )
        startActivity(intent)
    }


    override fun checkValidSession() {


        model.checkValidSession(this)
    }

    override fun setMenuToolbar() {

        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun signOutApp() {
        model.signOutApp(this)
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

            loadCatalogsFragment(center)
            model.addCenterToOrder(center.id!!, center.center_name!!, center.imageUrl)

        } else {
            AlertDialogQuestion(
                this,
                "Shop",
                getString(R.string.alert_dialog_order_activity_change_center).format(
                    model.getCurrentOrderCenterName(),
                    center.center_name
                ),
                resources.getString(R.string.alert_dialog_order_activity_change_center_button_confirm),
                resources.getString(R.string.alert_dialog_order_activity_button_deny),
                { d, i ->

                    model.cleanShop()
                    loadCatalogsFragment(center)

                },
                { d, i ->


                }).show()
        }

    }

    override fun selectCatalog(catalog: Catalog) {

        if (model.isPossibleChangeCatalog(catalog.sellerId)) {

            model.addSellerToOrder(catalog.sellerId, catalog.sellerName)
            loadProductsFragment(catalog)

        } else {
            AlertDialogQuestion(
                this,
                "Shop",
                getString(R.string.alert_dialog_order_activity_change_seller).format(
                    model.getCurrentOrderSellerName(),
                    catalog.sellerName
                ),
                resources.getString(R.string.alert_dialog_order_activity_change_center_button_confirm),
                resources.getString(R.string.alert_dialog_order_activity_button_deny),
                { _, _ ->

                    model.cleanProducts()
                    model.addSellerToOrder(catalog.sellerId, catalog.sellerName)
                    loadProductsFragment(catalog)

                },
                { d, i ->


                }).show()
        }
    }

    private fun init() {

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, CentersFragment()).commit()
    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun loadCatalogsFragment(center: Center) {

        var fragment = CatalogsFragment()
        var args = Bundle()

        args.putInt("centerId", center.id)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()

    }

    private fun loadProductsFragment(catalog: Catalog) {

        var args = Bundle()
        var fragment = ProductsFragment()
        args.putInt("catalogId", catalog.id)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()
    }

}
