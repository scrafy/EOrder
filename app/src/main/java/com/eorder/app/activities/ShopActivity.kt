package com.eorder.app.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.adapters.fragments.ShopProductsAdapter
import com.eorder.app.interfaces.IShopRepaintModel
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.application.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ShopActivity : BaseMenuActivity(), ISetAdapterListener, IShopRepaintModel {

    lateinit var model: ShopViewModel
    lateinit var adapter: ShopProductsAdapter
    lateinit var expandable: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        model = getViewModel()
        if (model.getProducts().isEmpty()) {

            var dialog = AlertDialogOk(
                R.layout.alert_dialog_ok,
                this,
                "Shop",
                "The shop is empty. Please add a product...",
                "OK"
            ) { d, i ->

                this.onBackPressed()

            }
            dialog.show()
        } else
            init()
    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun shopRepaintModel(
        view: View,
        product: Product,
        groupPosition: Int,
        childPosition: Int
    ) {

        var amountView = view.findViewById<TextView>(R.id.textView_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_product_list_heart)


        view.findViewById<TextView>(R.id.textView_product_list_amount).text =
            product.amount.toString()
        view.findViewById<TextView>(R.id.textView_product_list_price).text =
            product.price.toString()
        view.findViewById<TextView>(R.id.textView_product_list_category).text =
            product.category.name
        view.findViewById<TextView>(R.id.textView_product_list_name).text = product.name
        this.setAdapterListeners(
            view,
            this.adapter.getChild(groupPosition, childPosition)
        )


        if (product.amount == 0) {
            amountView.background = this.getDrawable(R.drawable.shape_amount_zero_products)
        } else {
            amountView.background = this.getDrawable(R.drawable.shape_amount_products)
        }

        if (product.favorite) {
            heart.setBackgroundResource(R.drawable.ic_purple_corazon)

        } else {
            heart.setBackgroundResource(R.drawable.ic_corazon_purple_outline)

        }
        amountView.setText(product.amount.toString())
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_product_list_remove).setOnClickListener { it ->

            if (product.amount > 0)
                product.amount--

            if (product.amount == 0) {
                model.removeProductFromShop(product)
                setProductsOnExpandable()
            }

            adapter.notifyDataSetChanged()
            setTotals()

        }

        view.findViewById<Button>(R.id.button_product_list_add).setOnClickListener { it ->

            product.amount++
            adapter.notifyDataSetChanged()
            setTotals()

        }

        view.findViewById<ImageView>(R.id.imgView_product_list_heart).setOnClickListener { it ->
            product.favorite = !product.favorite
            adapter.notifyDataSetChanged()

        }
    }


    private fun init() {

        var groups = model.getProducts().groupBy { p -> p.seller?.companyName ?: "" }

        adapter = ShopProductsAdapter(this, groups.keys.toList(), groups)
        expandable = findViewById<ExpandableListView>(R.id.expandable_shop_products)
        expandable.setAdapter(adapter)
        expandable.expandGroup(0)
        setTotals()

    }

    private fun setProductsOnExpandable() {

        val groups = model.getProducts().groupBy { p -> p.seller?.companyName ?: "" }
        adapter.items = groups
        adapter.groups = groups.keys.toList()
        if (model.getProducts().isEmpty())
            findViewById<Button>(R.id.button_shop_products).visibility = View.INVISIBLE
    }

    private fun setTotals() {

        findViewById<TextView>(R.id.textView_shop_amount_products).setText(model.getAmounfOfProducts().toString())
        findViewById<TextView>(R.id.textView_shop_amount_tax_base).setText(model.getTotalTaxBaseAmount().toString() + "€")
        findViewById<TextView>(R.id.textView_shop_amount_total_taxes).setText(model.getTotalTaxesAmount().toString() + "€")
        findViewById<TextView>(R.id.textView_shop_amount_total).setText(model.getTotalAmount().toString() + "€")

    }
}
