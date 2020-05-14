package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eorder.app.R
import com.eorder.app.adapters.OrderDoneAdapter
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.OrderDoneViewModel
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.extensions.convertToString
import com.eorder.domain.factories.Gson
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_order_done.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class OrderDoneActivity : BaseMenuActivity(), IRepaintModel, ISetAdapterListener,
    IShowSnackBarMessage {

    private lateinit var model: OrderDoneViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderDoneAdapter
    private lateinit var orders: List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_done)
        model = getViewModel()
        setObservers()
        setMenuToolbar()
        init()

    }

    private fun setObservers() {

        model.getOrdersDoneResultObservable().observe(this, Observer<ServerResponse<List<Order>>> {

            orders = it.ServerData?.Data ?: listOf()
            if (orders.isEmpty()) {
                showMessageFiniteTime(resources.getString(R.string.order_done_activity_no_orders))
            } else {
                adapter.orders = orders.sortedByDescending { o -> o.createdAt }
                adapter.notifyDataSetChanged()
            }

        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun signOutApp() {
        model.signOutApp(this)
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        view.findViewById<Button>(R.id.button_order_list_repeat).setOnClickListener {

            if (model.isShopEmpty()) {

                model.setOrder(obj as Order)
                startActivity(Intent(this, ShopActivity::class.java))
            } else {
                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.shop),
                    resources.getString(R.string.order_done_activity_alert_dialog_message),
                    resources.getString(R.string.yes),
                    resources.getString(R.string.no),
                    { _, _ ->
                        model.setOrder(obj as Order)
                        startActivity(Intent(this, ShopActivity::class.java))
                        //showFloatingButton()
                    },
                    { _, _ -> }

                ).show()
            }
        }

        view.findViewById<TextView>(R.id.textView_order_done_activity_detail).setOnClickListener {

            val intent = Intent(this, CartBreakdownActivity::class.java)
            intent.putExtra("order", Gson.Create().toJson(obj as Order))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        model.getOrdersDoneByUser(this)
    }

    override fun showMessage(message: String) {

        showMessage(message, Snackbar.LENGTH_INDEFINITE)

    }


    private fun showMessage(message: String, duration: Int) {

        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.frameLayout_order_done_activity_root),
            resources.getString(R.string.close),
            message,
            duration
        ).show()
    }


    private fun showMessageFiniteTime(message: String) {

        showMessage(message, Snackbar.LENGTH_LONG)
    }

    override fun repaintModel(view: View, model: Any?) {

        val order = model as Order
        view.findViewById<TextView>(R.id.textView_order_done_list_provider_name).text =
            order.seller.sellerName
        view.findViewById<TextView>(R.id.textView_order_list_total_amount).text =
            order.total.toString() + "€"


        view.findViewById<TextView>(R.id.textView_order_done_list_ref).text = "Ref. ${order.id}"
        view.findViewById<TextView>(R.id.textView_order_done_list_date).text =
            order.createdAt?.convertToString()

        try {
            Glide.with(this).load(order.center.imageUrl).circleCrop()
                .into(view.findViewById(R.id.textView_order_done_list_center_image))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById(R.id.textView_order_done_list_center_image))
        }
    }

    private fun init() {

        adapter = OrderDoneAdapter(

            listOf()
        )
        recyclerView = recicleView_orders_done_activity
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()


    }
}
