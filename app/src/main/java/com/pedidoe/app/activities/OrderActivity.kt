package com.pedidoe.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pedidoe.app.R
import com.pedidoe.app.com.eorder.app.interfaces.IOpenProductCalendar
import com.pedidoe.app.com.eorder.app.interfaces.ISelectCategory
import com.pedidoe.app.fragments.CatalogsFragment
import com.pedidoe.app.fragments.CategoriesFragment
import com.pedidoe.app.fragments.CentersFragment
import com.pedidoe.app.fragments.ProductsFragment
import com.pedidoe.app.interfaces.*
import com.pedidoe.app.viewmodels.OrderViewModel
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.AlertDialogQuestion
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.ICheckValidSession
import com.pedidoe.domain.factories.Gson
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.Catalog
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class OrderActivity : BaseMenuActivity(), ISelectCenter, ISelectCatalog,IFavoriteIconClicked,
    IShowSnackBarMessage, IToolbarSearch, IOnShopToolbarIconClicked, IOpenProductCalendar,
    ISelectCategory, ICheckValidSession {

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


    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun getSearchFromToolbar(search: String) {

        val fragment = this.supportFragmentManager.fragments.last{ f -> f is IToolbarSearch }
        (fragment as IToolbarSearch).getSearchFromToolbar(
            search
        )
    }

    override fun onFavoriteIconClicked() {

        val fragment = this.supportFragmentManager.fragments.last{ f -> f is IToolbarSearch }
        (fragment as IFavoriteIconClicked).onFavoriteIconClicked()
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

    override fun selectCenter(center: Center) {

        this.center = center
        if (model.isPossibleChangeCenter(center)) {

            model.getCatalogByCenter(center.id)
            model.addCenterToOrder(center.id!!, center.name!!, center.imageUrl, center.buyerId)

        } else {
            AlertDialogQuestion(
                this,
                "Shop",
                getString(R.string.alert_dialog_order_activity_change_center).format(
                    model.getCurrentOrderCenterName(),
                    center.name
                ),
                resources.getString(R.string.alert_dialog_order_activity_change_center_button_confirm),
                resources.getString(R.string.alert_dialog_order_activity_button_deny),
                { d, i ->

                    model.cleanShop()
                    model.addCenterToOrder(center.id!!, center.name!!, center.imageUrl, center.buyerId)
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

            model.addSellerToOrder(catalog.sellerId, catalog.sellerName, catalog.primaryCode)
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
                    model.addSellerToOrder(catalog.sellerId, catalog.sellerName, catalog.primaryCode)
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

                centers = it.ServerData?.Data ?: listOf()

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
                        model.addCenterToOrder(center.id!!, center.name!!, center.imageUrl, center.buyerId)
                        model.getCatalogByCenter(center.id)
                    }
                }


            })

        model.getCatalogByCenterResult
            .observe((this as LifecycleOwner), Observer<ServerResponse<List<Catalog>>> {


                catalogs = it.ServerData?.Data ?: listOf()
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
                        model.addSellerToOrder(catalog.sellerId, catalog.sellerName, catalog.primaryCode)

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

        args.putBoolean("showViewProductsLink", false)
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
        args.putString("center", Gson.Create().toJson(this.center))
        fragment.arguments = args

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.linear_layout_center_fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    private fun loadCategoriesFragmentNoReplace() {

        var fragment = CategoriesFragment()
        var args = Bundle()

        args.putString("catalog", Gson.Create().toJson(this.catalog))
        args.putString("center", Gson.Create().toJson(this.center))
        fragment.arguments = args

        supportFragmentManager.beginTransaction()
            .add(R.id.linear_layout_center_fragment_container, fragment).commit()
    }

    private fun loadCatalogsFragment() {

        var fragment = CatalogsFragment()
        var args = Bundle()
        args.putString("catalogs", Gson.Create().toJson(catalogs))
        args.putInt("centerId", this.center.id)
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
        args.putInt("centerId", this.center.id)
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
