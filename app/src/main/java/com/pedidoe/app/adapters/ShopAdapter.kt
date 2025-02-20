package com.pedidoe.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.pedidoe.app.R
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.domain.models.Product

class ShopAdapter(var products: List<Product>, private val context: Context) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var v = LayoutInflater.from(context).inflate(R.layout.order_products_list, null)
        (context as IRepaintModel).repaintModel(v, products[position])
        return v
    }

    override fun getItem(position: Int): Any {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return products[position].id.toLong()
    }

    override fun getCount(): Int {
        return products.size
    }
}