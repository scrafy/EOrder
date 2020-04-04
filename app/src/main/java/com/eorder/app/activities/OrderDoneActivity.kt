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
import com.eorder.app.widgets.*
import com.eorder.app.viewmodels.OrderDoneViewModel
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Order
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_order_done.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception

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


    private fun loadImages(items: List<UrlLoadedImage>) =

        model.loadImages(items)
            .observe(this, Observer<List<UrlLoadedImage>> { items ->

                items.forEach { item ->

                    this.orders.filter { o -> o.center.centerId == item.id }
                        .forEach { o -> o.center.imageBase64 = item.imageBase64 }
                }
                adapter.notifyDataSetChanged()
            })


    private fun setObservers() {

        model.getOrdersDoneResultObservable().observe(this, Observer<ServerResponse<List<Order>>> {

            orders = it.serverData?.data ?: listOf()
            adapter.orders = orders.sortedByDescending { o -> o.createdAt }
            adapter.notifyDataSetChanged()
            val items = orders.filter { o -> o.center.centerImageUrl != null }.map { o ->
                UrlLoadedImage(
                    o.center.centerId!!,
                    null,
                    o.center.centerImageUrl!!
                )
            }
            loadImages(items.distinct())
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

        if (order.center.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.textView_order_done_list_center_image)
                    .setImageDrawable(GifDrawable(this.resources, R.drawable.loading))
            } catch (ex: Exception) {
                var t = ex
            }


        } else {
            view.findViewById<ImageView>(R.id.textView_order_done_list_center_image)
                .setImageBitmap(order.center.imageBase64?.toBitmap())
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
