package com.eorder.app.activities


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eorder.app.R
import com.eorder.app.adapters.ProductsAdapter
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IToolbarSearch
import com.eorder.app.viewmodels.FavoriteViewModel
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorites.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class FavoriteActivity : BaseMenuActivity(), IRepaintModel, ISetAdapterListener,
    IShowSnackBarMessage, IToolbarSearch {

    private lateinit var model: FavoriteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductsAdapter
    private var products: MutableList<Product> = mutableListOf()

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        model = getViewModel()
        setMenuToolbar()
        init()
        setObservers()
    }

    

    private fun setObservers() {

        model.getFavoriteProductsResultObservable()
            .observe(this, Observer<ServerResponse<List<Product>>> {

                this.products = it?.serverData?.data?.toMutableList() ?: mutableListOf()

                if (this.products.isEmpty()) {
                    adapter.products = products
                    adapter.notifyDataSetChanged()
                    showMessageFiniteTime("There are not any product on your favorite list yet")
                } else {
                    this.products.map { p -> p.favorite = true }
                    adapter.products = this.products
                    adapter.notifyDataSetChanged()
                }


            })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

    }

    override fun showMessage(message: String) {
        showMessage(message, Snackbar.LENGTH_INDEFINITE)
    }

    override fun getSearchFromToolbar(search: String) {
        adapter.products =
            products.filter { p -> p.name.toLowerCase().contains(search.toLowerCase()) }
        adapter.notifyDataSetChanged()
    }


    override fun setMenuToolbar() {
        currentToolBarMenu["search_menu"] = R.menu.search_menu
        setActionBar(currentToolBarMenu, true, false)
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun signOutApp() {
        model.signOutApp(this)
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    
    private fun showMessage(message: String, duration: Int) {

        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout),
            resources.getString(R.string.close),
            message,
            duration
        ).show()
    }


    
    private fun showMessageFiniteTime(message: String) {

        showMessage(message, Snackbar.LENGTH_LONG)
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = (obj as Product)

        view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
            .setOnClickListener { v ->

                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.favorite_activity_alert_dialog_title),
                    resources.getString(R.string.favorite_activity_alert_dialog_message),
                    resources.getString(R.string.yes),
                    resources.getString(R.string.no),
                    { _, _ ->

                        model.removeProductFromFavorites(this, product.id)
                        products.remove(product)
                        if (products.isEmpty())
                            this.onBackPressed()

                        adapter.notifyDataSetChanged()
                    },
                    { _, _ -> }
                ).show()
            }
    }

    override fun onResume() {
        model.loadFavoriteProducts(this)
        super.onResume()
    }

    override fun repaintModel(view: View, model: Any?) {

        val product = (model as Product)
        view.findViewById<LinearLayout>(R.id.linearLayout_product_list_info_container)
            .removeView(view.findViewById<TextView>(R.id.textView_products_list_price))
        view.findViewById<TextView>(R.id.textView_products_list_product_name).text = product.name
        view.findViewById<TextView>(R.id.textView_products_list_category).text = product.category
        view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
            .setBackgroundResource(R.drawable.ic_corazon)


        if (product.favorite) {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon)

        } else {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon_outline)

        }

        try {
            Glide.with(this).load(product.imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_products_list_image_product))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_products_list_image_product))
        }

        view.findViewById<LinearLayout>(R.id.linearLayout_product_list_add_container)
            .removeView(view.findViewById<LinearLayout>(R.id.linearLayout_product_list_add_container_icons))

    }


    private fun init() {

        var layout = GridLayoutManager(this, 2)
        adapter = ProductsAdapter(

            listOf()
        )
        recyclerView = recicleView_favorites_activity
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            GridLayoutItemDecoration(
                2,
                50,
                true
            )
        )

    }
}
