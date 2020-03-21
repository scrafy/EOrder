package com.eorder.app.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import com.eorder.app.adapters.ShopProductsAdapter
import com.eorder.app.interfaces.IShopRepaintModel
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.ShopViewModel
import com.eorder.application.extensions.toBitmap
import com.eorder.domain.models.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.eorder.app.R


class ShopActivity : BaseMenuActivity(), ISetAdapterListener, IShopRepaintModel {

    lateinit var model: ShopViewModel
    lateinit var adapter: ShopProductsAdapter
    lateinit var expandable: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        model = getViewModel()
        isShopEmpty()

    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = com.eorder.app.R.menu.main_menu
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
            this.adapter.getChild(groupPosition, childPosition)
        )


        if (product.amount == 0) {
            amountView.background = this.getDrawable(R.drawable.shape_amount_zero_products)
        } else {
            amountView.background = this.getDrawable(R.drawable.shape_amount_products)
        }

        if (product.favorite) {
            heart.setBackgroundResource(R.drawable.ic_corazon)

        } else {
            heart.setBackgroundResource(R.drawable.ic_corazon_outline)

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
                if (model.getProducts().isEmpty()){
                    this.onBackPressed()
                }
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

    private fun setListeners(){

       /* expandable.setOnGroupClickListener { parent, view, groupPosition, id ->

            //setListViewHeight((view as ExpandableListView),groupPosition)
            true
        }*/
    }

    private fun setListViewHeight(
        listView: ExpandableListView,
        group: Int
    ) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)

            totalHeight += groupItem.measuredHeight

            if (listView.isGroupExpanded(i) && i != group || !listView.isGroupExpanded(i) && i == group) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem = listAdapter.getChildView(
                        i, j, false, null,
                        listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)

                    totalHeight += listItem.measuredHeight

                }
            }
        }

        val params = listView.layoutParams
        var height = totalHeight + listView.dividerHeight * (listAdapter.groupCount - 1)
        if (height < 10)
            height = 200
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()

    }

    private fun init() {

        var groups = model.getProducts().groupBy { p -> p.category }

        adapter = ShopProductsAdapter(this, groups.keys.toList(), groups)
        expandable = findViewById<ExpandableListView>(R.id.expandable_shop_products)
      //  expandable.setAdapter(adapter)
       // expandable.expandGroup(0)
        setTotals()

    }

    private fun setProductsOnExpandable() {

        val groups = model.getProducts().groupBy { p -> p.category }
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

            ) { d, i ->

                this.onBackPressed()

            }
            dialog.show()
        } else
            init()
    }

}
