package com.pedidoe.app.adapters.fragments


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedidoe.app.R
import com.pedidoe.app.interfaces.ISetAdapterListener
import com.pedidoe.app.fragments.ProductsFragment
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.domain.models.Product


class OrderProductAdapter(private val fragment: ProductsFragment) :
    RecyclerView.Adapter<OrderProductAdapter.ProductViewHolder>() {

    var products: MutableList<Product> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_products_list, parent, false)
        return ProductViewHolder(
            view,
            fragment
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.setData(products[position])
    }

    fun resetProducts(){

        products.clear()
        notifyDataSetChanged()
    }


    class ProductViewHolder(
        private val view: View,
        private val fragment: ProductsFragment
    ) : RecyclerView.ViewHolder(view) {


        fun setData(product: Product) {

            (fragment as ISetAdapterListener).setAdapterListeners(view, product)
            (fragment as IRepaintModel).repaintModel(view, product)

        }
    }
}