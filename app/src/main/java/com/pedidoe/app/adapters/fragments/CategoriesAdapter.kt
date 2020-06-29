package com.pedidoe.app.com.eorder.app.adapters.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.pedidoe.app.R
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.interfaces.ISetAdapterListener
import com.pedidoe.domain.models.Category

class CategoriesAdapter(private val categories: List<Category>, private val fragment: Fragment) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var v = LayoutInflater.from(parent?.context).inflate(R.layout.category_list, null)
        (fragment as IRepaintModel).repaintModel(v, categories[position])
        (fragment as ISetAdapterListener).setAdapterListeners(v, categories[position])
        return v

    }

    override fun getItem(position: Int): Any {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return categories[position].id.toLong()
    }

    override fun getCount(): Int {
        return categories.size
    }
}