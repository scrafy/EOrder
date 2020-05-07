package com.eorder.app.helpers

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.eorder.app.R


class FilterProductSpinners(

    private val context: Context,
    private val categories: List<String> = listOf(),
    private val SpinnerCategories: SpinnerExtension,
    private val SpinnerOrder: SpinnerExtension,
    private val onSelectedCategory: (position: Int) -> Unit,
    private val onSelectedOrder: (position: Int) -> Unit,
    private val itemLayout: Int
) {

    private val order: MutableList<String> = mutableListOf()

    init {
        setCategoryItemsSpinner()
        setOrderItems()
    }

    fun getCategories() = categories

    private fun setCategoryItemsSpinner() {

        val categoriesAdapter = ArrayAdapter<String>(
            context,
            itemLayout,
            categories
        )
        SpinnerCategories.adapter = categoriesAdapter
        categoriesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text_color)
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

                    onSelectedCategory(position)
                }

            }
    }

    private fun setOrderItems() {

        order.add("A-Z")
        order.add("Z-A")
        order.add(context.getString(R.string.product_order_by_price_low))
        order.add(context.getString(R.string.product_order_by_price_high))

        val orderAdapter = ArrayAdapter<String>(
            context,
            itemLayout,
            order
        )
        SpinnerOrder.adapter = orderAdapter
        orderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text_color)
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
                onSelectedOrder(position)
            }

        }
    }

}