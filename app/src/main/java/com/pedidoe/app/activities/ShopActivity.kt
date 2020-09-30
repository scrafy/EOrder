package com.pedidoe.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pedidoe.app.R
import com.pedidoe.app.adapters.ShopAdapter
import com.pedidoe.app.helpers.LoadImageHelper
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.viewmodels.ShopViewModel
import com.pedidoe.app.widgets.AlertDialogInput
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.AlertDialogQuestion
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.ICheckValidSession
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_shop.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ShopActivity : BaseActivity(), IRepaintModel,
    IShowSnackBarMessage, ICheckValidSession {

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
        init()
        this.setSupportActionBar(toolbar as Toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        isShopEmpty()
        adapter.notifyDataSetChanged()
        model.getOrderTotalsSummary()

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


        try {
            Glide.with(this).load(product.imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product))
        }


        view.findViewById<TextView>(R.id.textView_order_product_list_amount).text =
            product.amount.toString()
        view.findViewById<TextView>(R.id.textView_order_product_list_price).text =
            if (product.price == 0F) "" else product.price.toString() + "€"


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

        if (!product.amountsByDay.isNullOrEmpty()) {

            view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
                .setImageDrawable(resources.getDrawable(R.drawable.ic_calendario_confirmado))
        }

    }


    fun setObservers() {

        model.confirmOrderResult.observe(this, Observer<ServerResponse<Any>> {

            model.cleanShop(this)
            model.writeShopToSharedPreferencesOrder(this)
            AlertDialogOk(
                this, resources.getString(R.string.alert_dialog_confirm_order_title),
                resources.getString(R.string.alert_dialog_confirm_order_message), "OK"
            ) { _, _ ->
                navigateUpTo(Intent(this, LandingActivity::class.java))
            }.show()

        })

        model.getFavoriteProductsIdsResult.observe(
            this as LifecycleOwner,
            Observer<ServerResponse<List<Int>>> {

                val ids = it.ServerData?.Data

                if (!ids.isNullOrEmpty()) {
                    this.products.forEach { p -> p.favorite = false }
                    this.products.filter { p ->
                        p.id in ids
                    }.forEach { p -> p.favorite = true }
                }
                adapter.products = products
                adapter.notifyDataSetChanged()

            })

        model.summaryTotalsOrderResult.observe(this, Observer<Any> {

            this.products = model.getProducts()
            setProductsFavoriteState()
            this.setTotals()
        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }

    private fun setAdapterListeners(view: View, obj: Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_order_product_list_remove).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.alert_dialog_shop_dialog_title_calendar),
                    resources.getString(R.string.shop_activity_dialog_message_reset_calendar),
                    resources.getString(R.string.modify),
                    resources.getString(R.string.cancel),
                    { d, i ->

                        decrementProduct(product)
                    },
                    { d, i -> }
                ).show()
            } else {
                decrementProduct(product)
            }

        }

        view.findViewById<Button>(R.id.button_order_product_list_add).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {
                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.alert_dialog_shop_dialog_title_calendar),
                    resources.getString(R.string.shop_activity_dialog_message_reset_calendar),
                    resources.getString(R.string.modify),
                    resources.getString(R.string.cancel),
                    { d, i ->

                        incrementProduct(product)
                    },
                    { d, i -> }
                ).show()
            } else {
                incrementProduct(product)
            }

        }

        view.findViewById<ImageView>(R.id.imgView_order_product_list_heart).setOnClickListener {
            product.favorite = !product.favorite
            adapter.notifyDataSetChanged()

            if (product.favorite)
                model.saveProductAsFavorite(product.id)
            else
                model.deleteProductFromFavorites(product.id)
        }

        view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
            .setOnClickListener {

                ProductCalendarActivity.product = product
                var intent = Intent(this, ProductCalendarActivity::class.java)
                startActivity(intent)
            }

        view.findViewById<TextView>(R.id.textView_order_product_list_amount).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.alert_dialog_shop_dialog_title_calendar),
                    resources.getString(R.string.shop_activity_dialog_message_reset_calendar),
                    resources.getString(R.string.modify),
                    resources.getString(R.string.cancel),
                    { d, i ->

                        modifyAmountOfProduct(product)
                    },
                    { d, i -> }
                ).show()
            } else {
                modifyAmountOfProduct(product)
            }
        }
    }

    private fun decrementProduct(product: Product) {

        if (product.amount > 0)
            product.amount--

        if (product.amount == 0) {
            model.removeProductFromShop(product)
            if (model.getProducts().isEmpty()) {
                this.onBackPressed()
            }

        }
        product.amountsByDay = null
        adapter.notifyDataSetChanged()
        model.getOrderTotalsSummary()
    }

    private fun incrementProduct(product: Product) {

        product.amount++
        adapter.notifyDataSetChanged()
        product.amountsByDay = null
        model.getOrderTotalsSummary()
    }

    private fun modifyAmountOfProduct(product: Product) {

        var dialog: AlertDialogInput? = null
        dialog = AlertDialogInput(
            this,
            "",
            "",
            resources.getString(R.string.add),
            resources.getString(R.string.cancel),
            { d, i ->


                if (dialog?.input?.text.isNullOrEmpty()) {
                    product.amount = 0
                } else {
                    product.amount = Integer(dialog?.input?.text.toString()).toInt()
                }
                if (product.amount == 0) {

                    model.removeProductFromShop(product)
                    if (model.getProducts().isEmpty()) {
                        this.onBackPressed()
                    }

                }
                model.getOrderTotalsSummary()
                product.amountsByDay = null
                adapter.notifyDataSetChanged()

            },
            { d, i -> })
        dialog.show()
    }

    private fun init() {

        products = model.getProducts()
        adapter = ShopAdapter(products, this)
        listView = findViewById<ExpandableListView>(R.id.listView_activity_shop_product_list)
        listView.adapter = adapter

    }

    private fun setProductsFavoriteState() {

        model.getFavoriteProductsIds()

    }

    private fun setTotals() {

        findViewById<TextView>(R.id.textView_shop_amount_products).text =
            model.getAmountOfProducts().toString()
        findViewById<TextView>(R.id.textView_shop_amount_tax_base).text =
            model.getTotalBaseAmount().toString() + "€"
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
        }
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

                    model.cleanShop(this)
                    this.onBackPressed()
                },
                { _, _ -> }
            ).show()


        }

        textview_shop_follow_buying.setOnClickListener{

            val intent = Intent()
            intent.putExtra("loadCategoriesFragment", true)
            intent.setClass(this, OrderActivity::class.java)
            startActivity(intent)
        }

        textView_activity_shop_breakdown.setOnClickListener {

            startActivity(Intent(this, CartBreakdownActivity::class.java))
        }
    }

}
