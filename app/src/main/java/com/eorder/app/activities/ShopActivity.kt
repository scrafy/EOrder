package com.eorder.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.eorder.app.adapters.ShopProductsAdapter
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.widget.ExpandableListView
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.activities.BaseActivity
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.domain.interfaces.IShowSnackBarMessage
import com.eorder.application.extensions.toBitmap
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_shop.*


class ShopActivity : BaseActivity(), IRepaintModel,
    IShowSnackBarMessage {

    private lateinit var model: ShopViewModel
    private lateinit var adapter: ShopProductsAdapter
    private lateinit var listView: ListView


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


        view.findViewById<ImageView>(R.id.imgView_product_list_img_product)
            .setImageBitmap(product.imageBase64?.toBitmap())
        view.findViewById<TextView>(R.id.textView_product_list_amount).text =
            product.amount.toString()
        view.findViewById<TextView>(R.id.textView_product_list_price).text =
            product.price.toString()
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

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.manageExceptionService.manageException(this, ex)

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
            setTotals()

        }

        view.findViewById<Button>(R.id.button_product_list_add).setOnClickListener {

            product.amount++
            adapter.notifyDataSetChanged()
            setTotals()

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

        var products = model.getProducts()
        setProductsFavoriteState(products)
        adapter = ShopProductsAdapter(products, this)
        listView = findViewById<ExpandableListView>(R.id.listView_activity_shop_product_list)
        listView.adapter = adapter
        setTotals()
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

    private fun setListeners() {

        button_shop_activity_confirm_order.setOnClickListener {

            model.confirmOrder()
        }
    }

}
