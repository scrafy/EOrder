package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.adapters.ShopAdapter
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_product_calendar.*
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.activity_shop.toolbar
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ShopActivity : BaseActivity(), IRepaintModel,
    IShowSnackBarMessage {

    private lateinit var model: ShopViewModel
    private lateinit var adapter: ShopAdapter
    private lateinit var listView: ListView
    private lateinit var products: List<Product>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        model = getViewModel()
        setObservers()
        setListeners()
        isShopEmpty()

    }

    override fun onResume() {
        super.onResume()
        isShopEmpty()
        adapter.notifyDataSetChanged()
        model.getOrderTotalsSummary()
        this.setSupportActionBar(toolbar as Toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        this.onBackPressed()
        return true
    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<FrameLayout>(R.id.frameLayout_activity_shop_main_container),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun checkValidSession() {

        model.checkValidSession(this)
    }


    override fun repaintModel(
        view: View,
        model: Any?

    ) {

        var product = (model as Product)
        var amountView = view.findViewById<TextView>(R.id.textView_order_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_order_product_list_heart)


        if (product.image != null)
            view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product).setImageBitmap(
                product.image
            )
        else
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product))


        view.findViewById<TextView>(R.id.textView_order_product_list_amount).text =
            product.amount.toString()
        view.findViewById<TextView>(R.id.textView_order_product_list_price).text =
            if (product.price == 0F) "N/A" else product.price.toString() + "€"

        view.findViewById<TextView>(R.id.textView_order_product_list_category).text =
            product.category
        view.findViewById<TextView>(R.id.textView_order_product_list_name).text = product.name
        this.setAdapterListeners(
            view,
            product
        )


        if (product.amount == 0) {
            amountView.background = this.getDrawable(R.drawable.shape_amount_zero_products)
        } else {
            amountView.background = this.getDrawable(R.drawable.shape_amount_products)
        }

        amountView.text = product.amount.toString()

        if (product.favorite) {
            heart.setBackgroundResource(R.drawable.ic_corazon)

        } else {
            heart.setBackgroundResource(R.drawable.ic_corazon_outline)

        }

    }


    fun setObservers() {

        model.getConfirmOrderResultObservable().observe(this, Observer<ServerResponse<Int>> {

            AlertDialogOk(
                this, resources.getString(R.string.alert_dialog_confirm_order_title),
                resources.getString(R.string.alert_dialog_confirm_order_message), "OK"
            ) { _, _ ->
                if (intent.getStringExtra("activity_name") != null)
                    navigateUpTo(Intent(this, LandingActivity::class.java))
                else
                    this.onBackPressed()
            }.show()

        })

        model.getSummaryTotalsOrderResultObservable()
            .observe(this, Observer<ServerResponse<Order>> {

                this.setTotals()
            })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }

    private fun setAdapterListeners(view: View, obj: Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_order_product_list_remove).setOnClickListener {

            if (product.amount > 0)
                product.amount--

            if (product.amount == 0) {
                model.removeProductFromShop(product)
                if (model.getProducts().isEmpty()) {
                    this.onBackPressed()
                }

            }

            adapter.notifyDataSetChanged()
            model.getOrderTotalsSummary()

        }

        view.findViewById<Button>(R.id.button_order_product_list_add).setOnClickListener {

            product.amount++
            adapter.notifyDataSetChanged()
            model.getOrderTotalsSummary()

        }

        view.findViewById<ImageView>(R.id.imgView_order_product_list_heart).setOnClickListener {
            product.favorite = !product.favorite
            adapter.notifyDataSetChanged()
            model.writeProductsFavorites(
                this,
                product.id
            )

        }

        view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar).setOnClickListener {

            ProductCalendarActivity.product = product
            var intent = Intent(this, ProductCalendarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {

        products = model.getProducts()
        if (products.filter { p -> p.image == null }.isNotEmpty())
            LoadImageHelper().loadImage(products.filter { p -> p.image == null }).observe(
                this as LifecycleOwner,
                Observer {

                    adapter.notifyDataSetChanged()
                })
        setProductsFavoriteState(products)
        adapter = ShopAdapter(products, this)
        listView = findViewById<ExpandableListView>(R.id.listView_activity_shop_product_list)
        listView.adapter = adapter
        model.getOrderTotalsSummary()

    }

    private fun setProductsFavoriteState(products: List<Product>) {

        products.forEach { p ->

            p.favorite = false
        }
        val favorites = model.loadFavoritesProducts(this)

        if (favorites != null)
            products.filter { p ->
                p.id in favorites
            }.forEach { p -> p.favorite = true }
    }

    private fun setTotals() {

        findViewById<TextView>(R.id.textView_shop_amount_products).text =
            model.getAmountOfProducts().toString()
        findViewById<TextView>(R.id.textView_shop_amount_tax_base).text =
            model.getTotalTaxBaseAmount().toString() + "€"
        findViewById<TextView>(R.id.textView_shop_amount_total_taxes).text =
            model.getTotalTaxesAmount().toString() + "€"
        findViewById<TextView>(R.id.textView_shop_amount_total).text =
            model.getTotalAmount().toString() + "€"
        findViewById<TextView>(R.id.textView_shop_center).text = model.getCenterName()
        findViewById<TextView>(R.id.textView_shop_seller).text = model.getSellerName()

    }

    private fun isShopEmpty() {

        if (model.getProducts().isEmpty()) {

            this.onBackPressed()
        } else
            init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners() {

        button_shop_activity_confirm_order.setOnClickListener {

            AlertDialogQuestion(
                this,
                resources.getString(R.string.shop),
                resources.getString(R.string.alert_dialog_shop_confirm_message),
                resources.getString(R.string.yes),
                resources.getString(R.string.no),
                { _, _ ->

                    model.saveOrder(this)
                    model.confirmOrder()
                },
                { _, _ -> }
            ).show()
        }

        imgView_shop_activity_empty_car.setOnClickListener {


            AlertDialogQuestion(
                this,
                resources.getString(R.string.shop),
                resources.getString(R.string.alert_dialog_shop_delete),
                resources.getString(R.string.yes),
                resources.getString(R.string.no),
                { _, _ ->

                    model.cleanShop()
                    this.onBackPressed()
                },
                { _, _ -> }
            ).show()
        }

        textView_activity_shop_breakdown.setOnClickListener {

            startActivity(Intent(this, CartBreakdownActivity::class.java))
        }
    }

}
