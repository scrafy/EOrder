package com.eorder.app.com.eorder.app.adapters.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.eorder.app.R
import com.eorder.app.interfaces.IShopRepaintModel
import com.eorder.application.models.Product


class ShopProductsAdapter(

    private val context: Context,
    var groups: List<String>,
    var items: Map<String, List<Product>>

) : BaseExpandableListAdapter() {


    override fun getGroup(groupPosition: Int): Any {
        return groups[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val group = groups[groupPosition]
        lateinit var view: View

        if (convertView == null) {

            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.shop_list_group, null)
            view.findViewById<TextView>(R.id.shop_group_name)?.text = groups[groupPosition]

        } else {
            view = convertView
            view.findViewById<TextView>(R.id.shop_group_name).text = groups[groupPosition]
        }
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {

        return items[groups[groupPosition]]?.size ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {

        var list: List<Product> = items[groups[groupPosition]]!!

        return list[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val product = this.getChild(groupPosition, childPosition) as Product
        lateinit var view: View

        if (convertView == null) {

            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.products_list, null)

        } else {
            view = convertView
        }
        (context as IShopRepaintModel).shopRepaintModel(view, product, groupPosition, childPosition)
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return groups.size
    }
}