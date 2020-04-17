package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.adapters.OrderDoneAdapter
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.widgets.*
import com.eorder.app.viewmodels.OrderDoneViewModel
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.application.extensions.*
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
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

            orders = it.serverData?.data ?: listOf()
            adapter.orders = orders.sortedByDescending { o -> o.createdAt }
            adapter.notifyDataSetChanged()
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
    }

    override fun onResume() {
        model.getOrdersDoneByUser(this)
        super.onResume()
    }

    override fun showMessage(message: String) {

        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.frameLayout_order_done_activity_root),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun repaintModel(view: View, model: Any?) {

        val order = model as Order
        view.findViewById<TextView>(R.id.textView_order_done_list_provider_name).text =
            order.seller.sellerName
        view.findViewById<TextView>(R.id.textView_order_list_total_amount).text =
            order.total.toString() + "€"
        view.findViewById<LinearLayout>(R.id.linearLayout_order_done_list_product_list_container)
            .removeAllViews()
        order.products.forEach { p ->

            var textView = TextView(this)
            textView.text = "${p.amount} x ${p.name}"
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.setTextColor(resources.getColor(R.color.secondaryText))
            view.findViewById<LinearLayout>(R.id.linearLayout_order_done_list_product_list_container)
                .addView(textView)
        }

        view.findViewById<TextView>(R.id.textView_order_done_list_ref).text = "Ref. ${order.id}"
        view.findViewById<TextView>(R.id.textView_order_done_list_date).text = order.createdAt.toCustomDateFormatString("dd/MM/yyyy HH:mm:ss")

        LoadImageHelper().loadImage(order.center.imageUrl, view.findViewById(R.id.textView_order_done_list_center_image), true )
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
