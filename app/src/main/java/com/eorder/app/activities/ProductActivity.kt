package com.eorder.app.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.eorder.app.R
import com.eorder.app.adapters.FavoriteProductsAdapter
import com.eorder.app.com.eorder.app.adapters.ProductSellerListAdapter
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.ProductViewModel
import com.eorder.app.widgets.SnackBar
import com.eorder.application.extensions.toBitmap
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception

class ProductActivity : BaseMenuActivity(), IShowSnackBarMessage,
    IRepaintModel, ISetAdapterListener {

    private lateinit var model: ProductViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var recyclerView: RecyclerView
    private lateinit var sellerAdapter: ProductSellerListAdapter
    private lateinit var productsAdapter: FavoriteProductsAdapter
    private lateinit var sellers: List<Seller>
    private var products: List<Product> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        model = getViewModel()
        init()
        setListeners()
        setMenuToolbar()
        setObservers()
        model.getSellers()

    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun onResume() {
        if (products.isNotEmpty()) {
            setFavorites()
            productsAdapter.notifyDataSetChanged()
        }

        super.onResume()
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

    override fun showMessage(message: String) {
        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.dwrLayout_drawerlayout),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun repaintModel(view: View, model: Any?) {


        if (model is Seller)
            repaintSellersList(view, model)

        if (model is Product)
            repaintProductList(view, model)
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = (obj as Product)

        view.findViewById<ImageView>(R.id.imgView_favorite_list_image_heart)
            .setOnClickListener {

                product.favorite = !product.favorite
                productsAdapter.notifyDataSetChanged()

                model.writeProductsFavorites(
                    this,
                    product.id
                )
            }

        view.findViewById<ImageView>(R.id.imgView_favorite_list_cart).setOnClickListener {

            model.addProductToShop(this, obj)
        }

        view.findViewById<TextView>(R.id.textView_favorite_list_add).setOnClickListener {

            model.addProductToShop(this, obj)
        }
    }

    private fun repaintSellersList(view: View, obj: Any?) {

        val seller = (obj as Seller)

        if (seller.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.imgView_sellers_list_image)
                    .setImageDrawable(GifDrawable(resources, R.drawable.loading))
            } catch (ex: Exception) {

            }

        } else {
            view.findViewById<ImageView>(R.id.imgView_sellers_list_image)
                .setImageBitmap(seller.imageBase64?.toBitmap())
        }
    }

    private fun repaintProductList(view: View, obj: Any?) {

        val product = (obj as Product)
        view.findViewById<TextView>(R.id.textView_favorite_list_product_name).text = product.name
        view.findViewById<TextView>(R.id.textView_favorite_list_price).text = "${product.price}€"
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

    private fun init() {
        viewPager = findViewById(R.id.viewPager_product_activity)
        recyclerView = findViewById(R.id.recView_product_activity_product_list)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(GridLayoutItemDecoration(2, 50, true))
        productsAdapter = FavoriteProductsAdapter(listOf())
        recyclerView.adapter = productsAdapter

    }

    private fun setListeners() {

        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                model.getProductsBySeller(sellers[position].id)
            }

        })
    }

    private fun loadImagesSellers(items: List<UrlLoadedImage>) {

        model.loadImagesSeller(items)
            .observe(this, Observer<List<UrlLoadedImage>> {
                items.forEach { item ->

                    sellers.find { c -> c.id == item.id }?.imageBase64 =
                        item.imageBase64
                }
                sellerAdapter = ProductSellerListAdapter(this, sellers)
                viewPager.adapter = sellerAdapter
            })
    }

    private fun loadImagesProducts(items: List<UrlLoadedImage>) {

        model.loadImagesProducts(items)
            .observe(this, Observer<List<UrlLoadedImage>> {
                items.forEach { item ->

                    products.find { c -> c.id == item.id }?.imageBase64 =
                        item.imageBase64
                }
                productsAdapter.notifyDataSetChanged()
            })
    }

    private fun setFavorites() {

        val favorites = model.loadFavoritesProducts(this)
        products.forEach { p -> p.favorite = false }
        if (favorites != null)
            products.filter { p ->
                p.id in favorites
            }.forEach { p -> p.favorite = true }
    }

    private fun setObservers() {

        model.getSellersResultObservable()
            .observe(this, Observer<ServerResponse<List<Seller>>> {

                sellers = it.serverData?.data ?: listOf()
                //chequear si hay sellers, sino, salir actividad
                model.getProductsBySeller(sellers[0].id)
                loadImagesSellers(sellers.filter { s -> s.imageUrl != null }.map { s ->
                    UrlLoadedImage(
                        s.id,
                        null,
                        s.imageUrl!!
                    )
                })
            })

        model.getProductsResultObservable()
            .observe(this, Observer<ServerResponse<List<Product>>> {

                products = it.serverData?.data ?: listOf()
                setFavorites()
                productsAdapter.products = products
                productsAdapter.notifyDataSetChanged()
                loadImagesProducts(products.filter { s -> s.imageUrl != null }.map { s ->
                    UrlLoadedImage(
                        s.id,
                        null,
                        s.imageUrl!!
                    )
                })
            })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

        model.getAddFavoriteProductObservable().observe(this, Observer<Any> {

            this.showFloatingButton()

        })

        model.getFavoriteProductsResultObservable()
            .observe(this, Observer<ServerResponse<List<Product>>> {

                val products = it.serverData?.data ?: listOf()
                this.products.filter { p -> p.id in products.map { p -> p.id } }
                    .forEach { p -> p.favorite = true }
            })
    }
}
