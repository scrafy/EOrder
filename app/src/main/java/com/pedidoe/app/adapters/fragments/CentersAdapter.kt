package com.pedidoe.app.adapters.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.pedidoe.app.R
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.interfaces.ISetAdapterListener
import com.pedidoe.domain.models.Center

class CentersAdapter(private val fragment: Fragment, var centers: List<Center>) : RecyclerView.Adapter<CentersAdapter.CenterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.centers_list, parent, false)

        return CenterViewHolder(
            view,
            fragment
        )
    }

    override fun getItemCount(): Int {
       return centers.size
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {

        holder.setData(centers[position])
    }


    class CenterViewHolder(private val view: View, private val fragment: Fragment) : RecyclerView.ViewHolder(view) {


        fun setData(center: Center){

            (fragment as IRepaintModel).repaintModel(view, center)
            (fragment as ISetAdapterListener).setAdapterListeners(view, center)
       }
    }
}