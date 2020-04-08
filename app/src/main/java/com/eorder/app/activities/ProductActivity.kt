package com.eorder.app.activities

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.eorder.app.R
import com.eorder.app.adapters.ProductsAdapter
import com.eorder.app.com.eorder.app.adapters.ProductSellerListAdapter
import com.eorder.app.helpers.FilterProductSpinners
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IToolbarSearch
import com.eorder.app.viewmodels.ProductViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.extensions.toBitmap
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.Seller
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.products_fragment.spinner_product_list_categories
import kotlinx.android.synthetic.main.products_fragment.spinner_product_list_order
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import java.lang.Exception

class ProductActivity : BaseMenuActivity(), IShowSnackBarMessage,
    IRepaintModel, ISetAdapterListener, IToolbarSearch {

    private lateinit var model: ProductViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var recyclerView: RecyclerView
    private lateinit var sellerAdapter: ProductSellerListAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var sellers: List<Seller>
    private var products: List<Product> = listOf()
    private var sellerSelected: Int = 0
    private lateinit var productSpinners: FilterProductSpinners
    private var filters: MutableMap<String, String?> = mutableMapOf()

    init {

        filters["category"] = null
        filters["order"] = null
        filters["search"] = null
    }


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
        currentToolBarMenu["search_menu"] = R.menu.search_menu
        setActionBar(currentToolBarMenu, true, false)
    }

    override fun getSearchFromToolbar(search: String) {

        if (search != "")
            filters["search"] = search
        else
            filters["search"] = null

        applyFilters()

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

        view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
            .setOnClickListener {

                product.favorite = !product.favorite
                productsAdapter.notifyDataSetChanged()

                model.writeProductsFavorites(
                    this,
                    product.id
                )
            }

        view.findViewById<ImageView>(R.id.imgView_products_list_cart).setOnClickListener {

            model.addProductToShop(this, obj)
        }

        view.findViewById<TextView>(R.id.textView_products_list_add).setOnClickListener {

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
        view.findViewById<TextView>(R.id.textView_products_list_product_name).text = product.name
        view.findViewById<TextView>(R.id.textView_products_list_price).text = "${product.price}€"
        view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
            .setBackgroundResource(R.drawable.ic_corazon)
        if (product.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.imgView_products_list_image_product)
                    .setImageDrawable(GifDrawable(this.resources, R.drawable.loading))
            } catch (ex: Exception) {

            }

        } else {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_product)
                .setImageBitmap(product.imageBase64?.toBitmap())
        }

        if (product.favorite) {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon)

        } else {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon_outline)

        }
    }

    private fun init() {

        viewPager = findViewById(R.id.viewPager_product_activity)
        recyclerView = findViewById(R.id.recView_product_activity_product_list)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            GridLayoutItemDecoration(
                2,
                50,
                true
            )
        )
        productsAdapter = ProductsAdapter(listOf())
        recyclerView.adapter = productsAdapter
    }

    private fun addDots(size: Int) {

        for (i in 0 until size) {
            val txt = TextView(this)
            txt.text = Html.fromHtml("&#8226;")
            txt.textSize = 35F
            txt.setTextColor(resources.getColor(R.color.color_transparent))
            findViewById<LinearLayout>(R.id.linearLayout_product_activity_dots).addView(txt)
        }
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

                findViewById<LinearLayout>(R.id.linearLayout_product_activity_dots).children.iterator()
                    .forEach { v -> (v as TextView).setTextColor(resources.getColor(R.color.color_transparent)) }

                (findViewById<LinearLayout>(R.id.linearLayout_product_activity_dots).getChildAt(
                    position
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText))
                model.getProductsBySeller(sellers[position].id)
                sellerSelected = sellers[position].id
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
                if (sellers.isEmpty()) {

                    AlertDialogOk(this, "Sellers", "Any seller has been found", "OK") { d, i ->
                        this.onBackPressed()
                    }.show()

                } else {
                    addDots(sellers.size)
                    sellerSelected = sellers[0].id
                    (findViewById<LinearLayout>(R.id.linearLayout_product_activity_dots).getChildAt(
                        0
                    ) as TextView).setTextColor(resources.getColor(R.color.primaryText))
                    model.getProductsBySeller(sellers[0].id)
                    loadImagesSellers(sellers.filter { s -> s.imageUrl != null }.map { s ->
                        UrlLoadedImage(
                            s.id,
                            null,
                            s.imageUrl!!
                        )
                    })
                }
            })

        model.getProductsResultObservable()
            .observe(this, Observer<ServerResponse<List<Product>>> {

                products = it.serverData?.data ?: listOf()
                setFavorites()
                productSpinners = FilterProductSpinners(
                    this,
                    products,
                    spinner_product_list_categories,
                    spinner_product_list_order,
                    { pos ->

                        onSelectedCategory(pos)
                    },
                    { pos ->

                        onSelectedOrder(pos)
                    }
                )
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

    }

    private fun applyFilters() {

        var filtered: List<Product> = listOf()

        if (filters["category"] != null) {

            if (filters["category"] != null && filters["category"]?.toInt() == 0) {

                filtered = products
            }
            if (filters["category"]?.toInt()!! > 0) {

                filtered =
                    products.filter { p -> p.category === productSpinners.getCategories()[filters["category"]?.toInt()!!] }
            }

        }

        if (filters["search"] != null) {

            filtered = filtered.filter { p ->
                p.name.toLowerCase().contains(filters["search"]?.toLowerCase()!!)

            }

        }


        if (filters["order"] != null) {

            when (filters["order"]?.toInt()!!) {

                0 -> {
                    filtered = filtered.sortedBy { p -> p.name }

                }
                1 -> {
                    filtered = filtered.sortedByDescending { p -> p.name }

                }
                2 -> {
                    filtered = filtered.sortedBy { p -> p.price }
                }
                3 -> {
                    filtered = filtered.sortedByDescending { p -> p.price }
                }
            }
        }

        productsAdapter.products = filtered
        productsAdapter.notifyDataSetChanged()
    }

    private fun onSelectedCategory(position: Int) {

        filters["category"] = position.toString()
        applyFilters()
    }

    private fun onSelectedOrder(position: Int) {

        filters["order"] = position.toString()
        applyFilters()
    }
}
