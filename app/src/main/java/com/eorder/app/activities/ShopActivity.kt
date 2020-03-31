package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import com.eorder.app.adapters.ShopAdapter
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.widget.ExpandableListView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.com.eorder.app.dialogs.AlertDialogQuestion
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Order
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_shop.*
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception

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

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun checkValidSession() {

        model.checkValidSession(this)
    }


    override fun repaintModel(
        view: View,
        model: Any?

    ) {

        var product = (model as Product)
        var amountView = view.findViewById<TextView>(R.id.textView_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_product_list_heart)


        if (product.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.imgView_product_list_img_product)
                    .setImageDrawable(GifDrawable(this.resources, R.drawable.loading))
            } catch (ex: Exception) {

            }


        } else {
            view.findViewById<ImageView>(R.id.imgView_product_list_img_product)
                .setImageBitmap(product.imageBase64?.toBitmap())
        }

        view.findViewById<TextView>(R.id.textView_product_list_amount).text =
            product.amount.toString()
        view.findViewById<TextView>(R.id.textView_product_list_price).text =
            if (product.price == 0F) "N/A" else product.price.toString()

        view.findViewById<TextView>(R.id.textView_product_list_category).text =
            product.category
        view.findViewById<TextView>(R.id.textView_product_list_name).text = product.name
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
        view.findViewById<Button>(R.id.button_product_list_remove).setOnClickListener {

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

        view.findViewById<Button>(R.id.button_product_list_add).setOnClickListener {

            product.amount++
            adapter.notifyDataSetChanged()
            model.getOrderTotalsSummary()

        }

        view.findViewById<ImageView>(R.id.imgView_product_list_heart).setOnClickListener {
            product.favorite = !product.favorite
            adapter.notifyDataSetChanged()
            model.writeProductsFavorites(
                this,
                model.getProducts().filter { p -> p.favorite }.map { p -> p.id })

        }
    }

    private fun init() {

        products = model.getProducts()
        setProductsFavoriteState(products)
        adapter = ShopAdapter(products, this)
        listView = findViewById<ExpandableListView>(R.id.listView_activity_shop_product_list)
        listView.adapter = adapter
        model.getOrderTotalsSummary()
        loadImages(products.filter { o -> o.imageUrl != null && o.imageBase64 == null }.map { o ->
            UrlLoadedImage(
                o.id!!,
                null,
                o.imageUrl!!
            )
        })
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

            var dialog = AlertDialogOk(
                this,
                "Shop",
                "The shop is empty. Please add a product...",
                "OK"

            ) { _, _ ->

                this.onBackPressed()

            }
            dialog.show()
        } else
            init()
    }

    private fun loadImages(items: List<UrlLoadedImage>) =

        model.loadImages(items)
            .observe(this, Observer<List<UrlLoadedImage>> { items ->

                items.forEach { item ->

                    this.products.find { o -> o.id == item.id }?.imageBase64 = item.imageBase64
                }
                adapter.notifyDataSetChanged()
            })

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
