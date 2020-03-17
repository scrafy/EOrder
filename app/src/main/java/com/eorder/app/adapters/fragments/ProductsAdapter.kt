package com.eorder.app.adapters.fragments


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.fragments.ProductsFragment
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.application.models.Product


class ProductAdapter(var products: List<Product>, private val fragment: ProductsFragment) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.products_list, parent, false)
        return ProductViewHolder(
            view,
            fragment
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.setData(products.get(position))
    }


    class ProductViewHolder(
        private val view: View,
        private val fragment: ProductsFragment
    ) : RecyclerView.ViewHolder(view) {


        private lateinit var product: Product


        fun setData(product: Product) {

            this.product = product
            (fragment as ISetAdapterListener).setAdapterListeners(view, this.product)
            (fragment as IRepaintModel).repaintModel(view, this.product)

        }
    }
}