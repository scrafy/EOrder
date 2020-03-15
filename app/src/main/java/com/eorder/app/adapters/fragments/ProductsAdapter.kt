package com.eorder.app.adapters.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.fragments.ProductsFragment
import com.eorder.application.models.Product


class ProductAdapter(var products: List<Product>, private val fragment: ProductsFragment) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.products_list, parent, false)
        return ProductViewHolder(
            view,
            parent.context,
            fragment
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.setData(products.get(position))
    }


    class ProductViewHolder(private val view: View, private val context: Context, private val fragment: ProductsFragment) : RecyclerView.ViewHolder(view) {

        private var name: TextView = view.findViewById<TextView>(R.id.textView_product_list_name)
        private var category: TextView = view.findViewById<TextView>(R.id.textView_product_list_category)
        private var price: TextView = view.findViewById<TextView>(R.id.textView_product_list_price)
        private var textViewAmount: TextView = view.findViewById<TextView>(R.id.textView_product_list_amount)
        private lateinit var product:Product


        private fun repaintModel(){

            var amountView = view.findViewById<TextView>(R.id.textView_product_list_amount)
            var heart = view.findViewById<ImageView>(R.id.imgView_product_list_heart)

            if (this.product.amount == 0){
                amountView.background = context.getDrawable(R.drawable.shape_amount_zero_products)
            }else{
                amountView.background = context.getDrawable(R.drawable.shape_amount_products)
            }

            if (this.product.favorite) {
                heart.setBackgroundResource(R.drawable.ic_purple_corazon)

            }
            else {
                heart.setBackgroundResource(R.drawable.ic_corazon_purple_outline)

            }
            amountView.setText(this.product.amount.toString())
        }


        fun setData(product: Product){

           this.product = product
           this.name.setText(product.name)
           this.category.setText(product.category.name)
           this.price.setText("${product.price.toString()}€")
           this.textViewAmount.setText(this.product.amount.toString())
           ( fragment as ISetAdapterListener).setAdapterListeners(view, this.product)
            repaintModel()
        }
    }
}