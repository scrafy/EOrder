package com.eorder.app.adapters.fragments

import com.eorder.infrastructure.models.Catalog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IShowProductsByCatalog


class CatalogsByCenterAdapter(var catalogs: List<Catalog>) : RecyclerView.Adapter<CatalogsByCenterAdapter.CatalogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.center_catalogs, parent, false)

        return CatalogViewHolder(
            view,
            parent.context
        )
    }

    override fun getItemCount(): Int {
        return catalogs.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {

        holder.setData(catalogs.get(position))
    }


    class CatalogViewHolder(private val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

        private var catalogName: TextView ? = null
        private var enabled: TextView ? = null
        private var imgProducts: ImageView? = null
        private var catalogId: Int? = null



        init{

            this.catalogName = view.findViewById<TextView>(R.id.textView_catalog_center_catalog_name)
            this.enabled = view.findViewById<TextView>(R.id.textView_catalog_center_enabled)
            this.imgProducts = view.findViewById<ImageView>(R.id.imgView_catalog_center_products)
            setListener()
        }

        private fun setListener() {

            view.findViewById<ImageView>(R.id.imgView_catalog_center_products).setOnClickListener{view ->

                (context as IShowProductsByCatalog).showProductsByCatalog(this.catalogId)
            }

        }

        fun setData(catalog: Catalog){

            this.catalogId = catalog.id
            this.catalogName?.setText(catalog.name)
            if (catalog.enabled) {
                this.enabled?.setText(R.string.enabled)
                this.enabled?.setTextColor(Color.GREEN)
            }else{
                this.enabled?.setText(R.string.disabled)
                this.enabled?.setTextColor(Color.RED)
            }
        }
    }
}