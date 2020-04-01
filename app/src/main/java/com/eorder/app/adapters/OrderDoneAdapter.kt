package com.eorder.app.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.domain.models.Order


class OrderDoneAdapter(var orders: List<Order>) :
    RecyclerView.Adapter<OrderDoneAdapter.OrderDoneViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDoneViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_done_list, parent, false)

        return OrderDoneViewHolder(
            view,
            parent.context
        )
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderDoneViewHolder, position: Int) {

        holder.setData(orders[position])
    }


    class OrderDoneViewHolder(private val view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {

        fun setData(order: Order) {

            (context as IRepaintModel).repaintModel(view, order)
            (context as ISetAdapterListener).setAdapterListeners(view, order)
        }
    }
}