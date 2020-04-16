package com.eorder.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.domain.models.Catalog


class ProductCatalogListAdapter(
    private val context: Context,
    var catalog: List<Catalog>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return catalog.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.viewpager_catalogs_list, container, false)
        container.addView(view)
        (context as IRepaintModel).repaintModel(view, catalog[position])
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}

