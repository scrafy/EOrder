package com.eorder.app.adapters.fragments

import com.eorder.infrastructure.models.Catalog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener


class CatalogsByCenterAdapter(private val fragment:Fragment, var catalogs: List<Catalog>) : RecyclerView.Adapter<CatalogsByCenterAdapter.CatalogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.center_catalogs, parent, false)

        return CatalogViewHolder(
            view,
            fragment
        )
    }

    override fun getItemCount(): Int {
        return catalogs.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {

        holder.setModel(catalogs.get(position))
    }


    class CatalogViewHolder(private val view: View, private val fragment: Fragment) : RecyclerView.ViewHolder(view) {

        private lateinit var catalog:Catalog


        fun setModel(catalog: Catalog){

            this.catalog = catalog
            (fragment as IRepaintModel).repaintModel(view, catalog)
            (fragment as ISetAdapterListener).setAdapterListeners(view, catalog)
        }
    }
}