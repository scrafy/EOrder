package com.eorder.app.adapters.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.models.Seller


class SellerAdapter(private val fragment: Fragment, var sellers: List<Seller>) : RecyclerView.Adapter<SellerAdapter.SellerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.sellers_list, parent, false)

        return SellerViewHolder(
            view,
            fragment
        )
    }

    override fun getItemCount(): Int {
        return sellers.size
    }

    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {

        holder.setData(sellers[position])
    }


    class SellerViewHolder(private val view: View, private val fragment: Fragment) : RecyclerView.ViewHolder(view) {


        private lateinit var seller: Seller

        fun setData(seller: Seller){

            this.seller = seller
            (fragment as IRepaintModel).repaintModel(view, seller)
            (fragment as ISetAdapterListener).setAdapterListeners(view, seller)
        }
    }
}