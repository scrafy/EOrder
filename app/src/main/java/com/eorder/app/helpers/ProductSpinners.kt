package com.eorder.app.helpers

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IProductSpinnerAdapter
import com.eorder.app.extensions.SpinnerExtension
import com.eorder.domain.models.Product


class ProductSpinners(

    private val context: Context,
    private val products: List<Product>,
    private val adapter: IProductSpinnerAdapter,
    private val SpinnerCategories: SpinnerExtension,
    private val SpinnerOrder: SpinnerExtension
) {

    fun filterBySelectedCategory() {
       SpinnerCategories.setSelection(SpinnerCategories.selectedItemPosition)
    }

    fun orderBySelectedItem() {
        SpinnerOrder.setSelection(SpinnerOrder.selectedItemPosition)
    }

    var productsFiltered: List<Product> = products
    var search:String? = null

    init {
        setCategoryItemsSpinner()
        setOrderItems()
    }

    private fun setCategoryItemsSpinner() {

        val categories: MutableList<String> = mutableListOf()

        categories.add(context.resources.getString(R.string.product_categories))
        products.map { p -> p.category }.distinct().forEach { s -> categories.add(s) }

        val categoriesAdapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            categories
        )
        SpinnerCategories.adapter = categoriesAdapter

        SpinnerCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        productsFiltered = products
                        adapter.products = products
                        adapter.notifyDataChanged()
                    } else {
                        productsFiltered =
                            products.filter { p -> p.category === categories[position] }
                        adapter.products = productsFiltered
                        adapter.notifyDataChanged()
                    }
                    SpinnerOrder.setSelection(SpinnerOrder.selectedItemPosition)

                }

            }
    }

    private fun setOrderItems() {

        val order: MutableList<String> = mutableListOf()

        order.add("A-Z")
        order.add("Z-A")
        order.add(context.getString(R.string.product_order_by_price_low))
        order.add(context.getString(R.string.product_order_by_price_high))

        val orderAdapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            order
        )
        SpinnerOrder.adapter = orderAdapter
        SpinnerOrder.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                    0 -> {
                        productsFiltered =
                            productsFiltered.sortedBy { p -> p.name }
                        adapter.products = productsFiltered
                        adapter.notifyDataChanged()

                    }
                    1 -> {
                        productsFiltered =
                            productsFiltered.sortedByDescending { p -> p.name }
                        adapter.products = productsFiltered
                        adapter.notifyDataChanged()
                    }
                    2 -> {
                        productsFiltered =
                            productsFiltered.sortedBy { p -> p.price };
                        adapter.products = productsFiltered
                        adapter.notifyDataChanged()
                    }
                    3 -> {
                        productsFiltered =
                            productsFiltered.sortedByDescending { p -> p.price }
                        adapter.products = productsFiltered
                        adapter.notifyDataChanged()
                    }
                }
            }

        }
    }

}