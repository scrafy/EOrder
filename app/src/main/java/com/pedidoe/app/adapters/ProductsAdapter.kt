package com.pedidoe.app.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedidoe.app.R
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.interfaces.ISetAdapterListener
import com.pedidoe.domain.models.Product

class ProductsAdapter :
    RecyclerView.Adapter<ProductsAdapter.FavoritesViewHolder>(){

    var products: MutableList<Product> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_products_list, parent, false)

        return FavoritesViewHolder(
            view,
            parent.context
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        holder.setData(products[position])
    }

    fun resetProducts(){

        products.clear()
        notifyDataSetChanged()
    }


    class FavoritesViewHolder(private val view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {

        fun setData(product: Product) {

            (context as IRepaintModel).repaintModel(view, product)
            (context as ISetAdapterListener).setAdapterListeners(view, product)
        }
    }
}