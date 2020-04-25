package com.eorder.app.adapters.fragments

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.models.ProductAmountByDay


@RequiresApi(Build.VERSION_CODES.O)
class ProductCalendarAdapter(
    var days: List<ProductAmountByDay>
) : RecyclerView.Adapter<ProductCalendarAdapter.ProductCatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCatalogViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.calendar_product_day, parent, false)
        return ProductCatalogViewHolder(
            view,
            parent.context
        )
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: ProductCatalogViewHolder, position: Int) {
        return holder.setModel(days[position])
    }


    class ProductCatalogViewHolder(private val view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {

        fun setModel(day: ProductAmountByDay) {

            (context as IRepaintModel).repaintModel(view, day)
            (context as ISetAdapterListener).setAdapterListeners(view, day)
        }
    }
}