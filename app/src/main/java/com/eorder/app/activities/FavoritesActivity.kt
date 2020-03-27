package com.eorder.app.activities

import FavoritesActivityViewModel
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.adapters.FavoriteProductsAdapter
import com.eorder.app.com.eorder.app.interfaces.IOnFloatinButtonShopClicked
import com.eorder.app.dialogs.AlertDialogOk
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_favorites.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception

class FavoritesActivity : BaseMenuActivity(), IRepaintModel, ISetAdapterListener, IOnFloatinButtonShopClicked {

    private lateinit var model: FavoritesActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteProductsAdapter
    private var products: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        model = getViewModel()
        setMenuToolbar()
        init()
        setObservers()
        model.loadFavoriteProducts(this)

    }

    private fun setObservers() {

        model.getfavoriteProductsResultObservable()
            .observe(this, Observer<ServerResponse<List<Product>>> {

                this.products = it.serverData?.data?.toMutableList() ?: mutableListOf()
                this.products.map { p -> p.favorite = true }
                adapter.products = this.products
                adapter.notifyDataSetChanged()
                loadProductImages()
            })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.manageExceptionService.manageException(this, ex)

        })


    }

    override fun setMenuToolbar() {
        currentToolBarMenu["main_menu"] = R.menu.main_menu
        setToolbarAndLateralMenu(currentToolBarMenu)
    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun onFloatingButtonClicked() {
        var intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
    }

    override fun signOutApp() {
        model.signOutApp(this)
    }

    override fun checkValidSession() {
        model.checkValidSession(this)
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = (obj as Product)

        view.findViewById<ImageView>(R.id.imgView_favorite_list_image_heart)
            .setOnClickListener { v ->

                AlertDialogOk(
                    this,
                    resources.getString(R.string.favorite_activity_alert_dialog_title),
                    resources.getString(R.string.favorite_activity_alert_dialog_message),
                    "OK"
                ) { _, _ ->

                    model.removeProductFromFavorites(this, product.id)
                    products.remove(product)
                    if (products.isEmpty())
                        this.onBackPressed()

                    adapter.notifyDataSetChanged()
                }.show()
            }
    }

    override fun onStart(){
        model.loadFavoriteProducts(this)
        super.onStart()
    }

    override fun repaintModel(view: View, model: Any?) {

        val product = (model as Product)
        view.findViewById<TextView>(R.id.textView_favorite_list_product_name).text = product.name
        view.findViewById<ImageView>(R.id.imgView_favorite_list_image_heart)
            .setBackgroundResource(R.drawable.ic_corazon)
        if (product.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.imgView_favorite_list_image_product)
                    .setImageDrawable(GifDrawable(this.resources, R.drawable.loading))
            } catch (ex: Exception) {

            }

        } else {
            view.findViewById<ImageView>(R.id.imgView_favorite_list_image_product)
                .setImageBitmap(product.imageBase64?.toBitmap())
        }

        if (product.favorite) {
            view.findViewById<ImageView>(R.id.imgView_favorite_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon)

        } else {
            view.findViewById<ImageView>(R.id.imgView_favorite_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon_outline)

        }

    }

    private fun loadProductImages() {

        var items = products.filter { p -> p.imageUrl != null }.map { p ->

            UrlLoadedImage(p.id, p.imageBase64, p.imageUrl!!)
        }

        model.loadImages(items)
            .observe(this, Observer<List<UrlLoadedImage>> { items ->

                items.forEach { item ->

                    this.products.find { c -> c.id == item.id }?.imageBase64 =
                        item.imageBase64
                }
                adapter.notifyDataSetChanged()
            })
    }

    private fun init() {

        var layout = GridLayoutManager(this, 2)
        adapter = FavoriteProductsAdapter(

            listOf()
        )
        recyclerView = recicleView_favorites_activity
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(GridLayoutItemDecoration(2,50,true))

    }
}
