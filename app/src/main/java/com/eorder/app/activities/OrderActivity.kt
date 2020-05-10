package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IOpenProductCalendar
import com.eorder.app.com.eorder.app.interfaces.ISelectCategory
import com.eorder.app.fragments.CatalogsFragment
import com.eorder.app.fragments.CategoriesFragment
import com.eorder.app.fragments.CentersFragment
import com.eorder.app.fragments.ProductsFragment
import com.eorder.app.interfaces.*
import com.eorder.app.viewmodels.OrderViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.factories.Gson
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class OrderActivity : BaseMenuActivity(), ISelectCenter, ISelectCatalog, IRepaintShopIcon,
    IShowSnackBarMessage, IToolbarSearch, IOnShopToolbarIconClicked, IOpenProductCalendar,
    ISelectCategory {

    private lateinit var model: OrderViewModel
    private lateinit var center: Center
    private lateinit var catalog: Catalog
    private lateinit var centers: List<Center>
    private lateinit var catalogs: List<Catalog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        model = getViewModel()
        setObservers()
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

        this.center = center
        if (model.isPossibleChangeCenter(center)) {

            model.getCatalogByCenter(center.id)
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
                    model.getCatalogByCenter(center.id)

                },
                { d, i ->


                }).show()
        }

    }

    override fun openProductCalendar(product: Product) {

        ProductCalendarActivity.product = product
        var intent = Intent(this, ProductCalendarActivity::class.java)
        startActivity(intent)
    }

    override fun selectCatalog(catalog: Catalog) {

        this.catalog = catalog
        if (model.isPossibleChangeCatalog(catalog.sellerId)) {

            model.addSellerToOrder(catalog.sellerId, catalog.sellerName)
            loadCategoriesFragment()

        } else {
            AlertDialogQuestion(
                this,
                "Shop",
                getString(R.string.alert_dialog_order_activity_change_seller).format(
                    model.getCurrentOrderSellerName(),
                    catalog.sellerName
                ),
                resources.getString(R.string.alert_dialog_order_activity_change_seller_button_confirm),
                resources.getString(R.string.alert_dialog_order_activity_button_deny),
                { _, _ ->

                    model.cleanProducts()
                    model.addSellerToOrder(catalog.sellerId, catalog.sellerName)
                    loadCategoriesFragment()

                },
                { d, i ->


                }).show()
        }


    }

    override fun selectCategory(data: String) {

        loadProductsFragment(data)

    }

    fun setObservers() {

        model.getCentersResult.observe(
            this as LifecycleOwner,
            Observer<ServerResponse<List<Center>>> {

                centers = it.serverData?.data ?: listOf()

                if (centers.isEmpty()) {
                    AlertDialogOk(
                        this,
                        resources.getString(R.string.centers),
                        resources.getString(R.string.alert_dialog_order_activity_no_center_message),
                        resources.getString(R.string.ok)
                    ) { d, i -> this.onBackPressed() }.show()
                } else {
                    if (centers.isNotEmpty() && centers.size > 1) {

                        loadCentersFragment()

                    } else {
                        center = centers[0]
                        model.addCenterToOrder(center.id!!, center.center_name!!, center.imageUrl)
                        model.getCatalogByCenter(center.id)
                    }
                }


            })

        model.getCatalogByCenterResult
            .observe((this as LifecycleOwner), Observer<ServerResponse<List<Catalog>>> {


                catalogs = it.serverData?.data ?: listOf()
                if (catalogs.isEmpty()) {
                    AlertDialogOk(
                        this,
                        resources.getString(R.string.catalogs),
                        resources.getString(R.string.alert_dialog_order_activity_no_catalog_message),
                        resources.getString(R.string.ok)
                    ) { d, i -> this.onBackPressed() }.show()
                } else {
                    if (catalogs.isNotEmpty() && catalogs.size > 1) {

                        if (centers.isNotEmpty() && centers.size == 1)
                            loadCatalogsFragmentNoReplace()
                        else
                            loadCatalogsFragment()
                    } else {
                        catalog = catalogs[0]
                        model.addSellerToOrder(catalog.sellerId, catalog.sellerName)

                        if (centers.isNotEmpty() && centers.size > 1)
                            loadCategoriesFragment()
                        else
                            loadCategoriesFragmentNoReplace()
                    }

                }
            })

        model.getErrorObservable()
            ?.observe(this as LifecycleOwner, Observer<Throwable> { ex ->

                model.getManagerExceptionService().manageException(this, ex)
            })


    }

    private fun loadCentersFragment() {

        var fragment = CentersFragment()
        var args = Bundle()

        args.putBoolean("showViewProductsLink", true)
        args.putString("centers", Gson.Create().toJson(centers))
        fragment.arguments = args

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, fragment).commit()
    }

    private fun init() {

        model.getCenters()
    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout),
            resources.getString(R.string.close),
            message
        ).show()
    }

    private fun loadCategoriesFragment() {

        var fragment = CategoriesFragment()
        var args = Bundle()

        args.putString("catalog", Gson.Create().toJson(this.catalog))
        fragment.arguments = args

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    private fun loadCategoriesFragmentNoReplace() {

        var fragment = CategoriesFragment()
        var args = Bundle()

        args.putString("catalog", Gson.Create().toJson(this.catalog))
        fragment.arguments = args

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, fragment).commit()
    }

    private fun loadCatalogsFragment() {

        var fragment = CatalogsFragment()
        var args = Bundle()
        args.putString("catalogs", Gson.Create().toJson(catalogs))
        fragment.arguments = args

        if (catalogs.isNotEmpty() && catalogs.size > 1) {

            this.supportFragmentManager.beginTransaction()
                .replace(R.id.linear_layout_center_fragment_container, fragment)
                .addToBackStack(null).commit()
        }

    }

    private fun loadCatalogsFragmentNoReplace() {

        var fragment = CatalogsFragment()
        var args = Bundle()
        args.putString("catalogs", Gson.Create().toJson(catalogs))
        fragment.arguments = args

        if (catalogs.isNotEmpty() && catalogs.size > 1) {

            this.supportFragmentManager.beginTransaction()
                .add(R.id.linear_layout_center_fragment_container, fragment).commit()
        }


    }

    private fun loadProductsFragment(data: String) {

        var args = Bundle()
        var fragment = ProductsFragment()
        args.putString("data", data)
        fragment.arguments = args
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()
    }

}
