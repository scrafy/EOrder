package com.eorder.app.com.eorder.app.adapters.fragments

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.IShowCatalogsByCenter
import com.eorder.app.com.eorder.app.interfaces.IShowCenterInfo
import com.eorder.infrastructure.models.Product


class ProductAdapter(var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.products_list, parent, false)

        return ProductViewHolder(
            view,
            parent.context
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.setData(products.get(position))
    }


    class ProductViewHolder(private val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

        private var name: TextView ? = null
        private var category: TextView ? = null
        private var price: TextView ? = null


        init{

            this.name = view.findViewById<TextView>(R.id.textView_product_list_name)
            this.category = view.findViewById<TextView>(R.id.textView_product_list_category)

            setListener()
        }

        private fun setListener() {


        }


        fun setData(product: Product){

           this.name?.setText(product.name)
           this.category?.setText(product.category.name)
        }
    }
}