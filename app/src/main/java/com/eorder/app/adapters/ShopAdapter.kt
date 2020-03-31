package com.eorder.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.domain.models.Product

class ShopAdapter(private val products: List<Product>, private val context: Context) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var v = LayoutInflater.from(context).inflate(R.layout.products_list, null)
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