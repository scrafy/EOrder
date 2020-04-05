package com.eorder.app.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.models.Product

class FavoriteProductsAdapter(var products: List<Product>) :
    RecyclerView.Adapter<FavoriteProductsAdapter.FavoritesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)

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


    class FavoritesViewHolder(private val view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {

        fun setData(product: Product) {

            (context as IRepaintModel).repaintModel(view, product)
            (context as ISetAdapterListener).setAdapterListeners(view, product)
        }
    }
}