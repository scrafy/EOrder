package com.pedidoe.app.adapters.fragments

import com.pedidoe.domain.models.Catalog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.pedidoe.app.R
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.interfaces.ISetAdapterListener


class CatalogsAdapter(private val fragment:Fragment, var catalogs: List<Catalog>) : RecyclerView.Adapter<CatalogsAdapter.CatalogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalogs_list, parent, false)

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

       fun setModel(catalog: Catalog){

            (fragment as IRepaintModel).repaintModel(view, catalog)
            (fragment as ISetAdapterListener).setAdapterListeners(view, catalog)
        }
    }
}