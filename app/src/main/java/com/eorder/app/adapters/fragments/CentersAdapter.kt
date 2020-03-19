package com.eorder.app.adapters.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.models.Center

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

        holder.setData(centers.get(position))
    }


    class CenterViewHolder(private val view: View, private val fragment: Fragment) : RecyclerView.ViewHolder(view) {


        private lateinit var center: Center

        fun setData(center: Center){

            this.center = center
            (fragment as IRepaintModel).repaintModel(view, center)
            (fragment as ISetAdapterListener).setAdapterListeners(view, center)
       }
    }
}