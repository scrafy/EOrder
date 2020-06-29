package com.pedidoe.app.activities

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.pedidoe.app.R
import com.pedidoe.app.adapters.ProductsAdapter
import com.pedidoe.app.adapters.SellerProducSellertListAdapter
import com.pedidoe.app.adapters.SellerProductCenterListAdapter
import com.pedidoe.app.helpers.FilterProductSpinners
import com.pedidoe.app.helpers.GridLayoutItemDecoration
import com.pedidoe.app.helpers.LoadImageHelper
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.interfaces.ISetAdapterListener
import com.pedidoe.app.interfaces.IToolbarSearch
import com.pedidoe.app.viewmodels.SellerProductViewModel
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.ICheckValidSession
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.Center
import com.pedidoe.domain.models.Product
import com.pedidoe.domain.models.Seller
import com.pedidoe.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_seller_product.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class SellerProductActivity : BaseMenuActivity(), IShowSnackBarMessage,
    IRepaintModel, ISetAdapterListener, IToolbarSearch, ICheckValidSession {

    private lateinit var model: SellerProductViewModel
    private lateinit var sellerViewPager: ViewPager
    private lateinit var centerViewPager: ViewPager
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var sellers: List<Seller>
    private var centers: List<Center> = listOf()
    private var products: List<Product> = listOf()
    private var sellerSelected: Int = 0
    private var centerSelected: Int = 0
    private lateinit var productSpinners: FilterProductSpinners
    private var filters: MutableMap<String, String?> = mutableMapOf()

    init {

        filters["category"] = null
        filters["order"] = null
        filters["search"] = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_product)
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
            findViewById<DrawerLayout>(R.id.frameLayout_activity_seller_product_container),
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun repaintModel(view: View, model: Any?) {


        if (model is Seller)
            repaintSellersList(view, model)

        if (model is Product)
            repaintProductList(view, model)

        if (model is Center)
            repaintCenterList(view, model)
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

            model.addProductToShop(this, obj, centers.first { it.id == centerSelected })
        }

        view.findViewById<TextView>(R.id.textView_products_list_add).setOnClickListener {

            model.addProductToShop(this, obj, centers.first { it.id == centerSelected })
        }
    }


    private fun repaintSellersList(view: View, obj: Any?) {

        var obj  = obj as Seller
        view.findViewById<TextView>(R.id.textView_sellers_list_sellers_name).text = obj.companyName

        try {
            Glide.with(this).load((obj).imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_seller_list_image))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_seller_list_image))
        }

    }

    private fun repaintCenterList(view: View, obj: Any?) {

        try {
            Glide.with(this).load((obj as Center).imageUrl).circleCrop()
                .into(view.findViewById<ImageView>(R.id.imgView_center_list_image))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_center_list_image))
        }

        view.findViewById<TextView>(R.id.textView_center_list_center_name).text = (obj as Center).name
    }

    private fun repaintProductList(view: View, obj: Any?) {

        val product = (obj as Product)
        view.findViewById<TextView>(R.id.textView_products_list_product_name).text = product.name
        view.findViewById<TextView>(R.id.textView_products_list_price).text = "${product.price}€"
        view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
            .setBackgroundResource(R.drawable.ic_corazon)


        try {
            Glide.with(this).load(product.imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_products_list_image_product))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_products_list_image_product))
        }

        if (product.favorite) {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon)

        } else {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon_outline)

        }

        view.findViewById<TextView>(R.id.textView_products_list_category).text = product.category
    }


    private fun init() {

        sellerViewPager = findViewById(R.id.viewPager_activity_seller_product_sellers)
        centerViewPager = findViewById(R.id.viewPager_activity_seller_product_centers)
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
        productsAdapter = ProductsAdapter()
        recyclerView.adapter = productsAdapter
    }

    private fun addDots(size: Int, v: LinearLayout) {

        for (i in 0 until size) {
            val txt = TextView(this)
            txt.text = Html.fromHtml("&#8226;")
            txt.textSize = 35F
            txt.setTextColor(resources.getColor(R.color.color_transparent))
            v.addView(
                txt
            )
        }
    }

    private fun setListeners() {

        textView_activity_seller_product_select_seller.setOnClickListener {

            expandableLayout.toggle()

            if (expandableLayout.isExpanded) {
                textView_activity_seller_product_select_seller.text =
                    "${textView_activity_seller_product_select_seller.text.substring(
                        0,
                        textView_activity_seller_product_select_seller.text.length - 1
                    )}▼"
            } else {
                textView_activity_seller_product_select_seller.text =
                    "${textView_activity_seller_product_select_seller.text.substring(
                        0,
                        textView_activity_seller_product_select_seller.text.length - 1
                    )}▲"
            }
        }

        textView_activity_seller_product_select_center.setOnClickListener {

            expandableLayout2.toggle()
            if (expandableLayout2.isExpanded) {
                textView_activity_seller_product_select_center.text =
                    "${textView_activity_seller_product_select_center.text.substring(
                        0,
                        textView_activity_seller_product_select_center.text.length - 1
                    )}▼"
            } else {
                textView_activity_seller_product_select_center.text =
                    "${textView_activity_seller_product_select_center.text.substring(
                        0,
                        textView_activity_seller_product_select_center.text.length - 1
                    )}▲"
            }
        }

        sellerViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                findViewById<LinearLayout>(R.id.linearLayout_activity_seller_product_dots_sellers).children.iterator()
                    .forEach { v ->
                        (v as TextView).setTextColor(
                            resources.getColor(
                                R.color.color_transparent,
                                null
                            )
                        )
                    }

                (findViewById<LinearLayout>(R.id.linearLayout_activity_seller_product_dots_sellers).getChildAt(
                    position
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))

                sellerSelected = sellers[position].id
                if (centerSelected != 0)
                    model.getProductsBySeller(centerSelected, sellerSelected)

            }

        })

        centerViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                findViewById<LinearLayout>(R.id.linearLayout_activity_seller_product_dots_centers).children.iterator()
                    .forEach { v ->
                        (v as TextView).setTextColor(
                            resources.getColor(
                                R.color.color_transparent,
                                null
                            )
                        )
                    }

                (findViewById<LinearLayout>(R.id.linearLayout_activity_seller_product_dots_centers).getChildAt(
                    position
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))

                centerSelected = centers[position].id
                model.getProductsBySeller(centerSelected, sellerSelected)

            }

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


                sellers = it.ServerData?.Data ?: listOf()
                sellerSelected = intent.getIntExtra("sellerId", sellers.first().id)
                sellerViewPager.adapter = SellerProducSellertListAdapter(this, sellers)
                sellerViewPager.adapter?.notifyDataSetChanged()
                addDots(sellers.size, linearLayout_activity_seller_product_dots_sellers)
                val index = sellers.indexOf(sellers.first { s -> s.id == sellerSelected })
                if (index == 0) {
                    (findViewById<LinearLayout>(R.id.linearLayout_activity_seller_product_dots_sellers).getChildAt(
                        0
                    ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))

                } else
                    sellerViewPager.currentItem = index

                model.getCenters()

            })

        model.getCentersResultObservable()
            .observe(this, Observer<ServerResponse<List<Center>>> {

                centers = it.ServerData?.Data ?: listOf()
                addDots(centers.size, linearLayout_activity_seller_product_dots_centers)
                centerViewPager.adapter = SellerProductCenterListAdapter(this, centers)
                centerViewPager.adapter?.notifyDataSetChanged()
                (findViewById<LinearLayout>(R.id.linearLayout_activity_seller_product_dots_centers).getChildAt(
                    0
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))
                centerSelected = centers[0].id
                model.getProductsBySeller(centerSelected, sellerSelected)
            })

        model.getProductsResultObservable()
            .observe(this, Observer<ServerResponse<List<Product>>> {

                products = it.ServerData?.Data ?: listOf()

                if (centers.isEmpty()) {

                    AlertDialogOk(
                        this,
                        resources.getString(R.string.centers),
                        resources.getString(R.string.seller_product_activity_dialog_no_centers_message),
                        resources.getString(R.string.ok)

                    ) { d, i ->

                    }.show()
                } else {
                    if (products.isEmpty()) {
                        AlertDialogOk(
                            this,
                            resources.getString(R.string.productos),
                            String.format(resources.getString(R.string.seller_product_activity_dialog_no_products_message), centers.find { c -> c.id == centerSelected }?.name, sellers.find { s -> s.id == sellerSelected }?.companyName ),
                            resources.getString(R.string.ok)

                        ) { d, i ->

                        }.show()
                        productsAdapter.products = products.toMutableList()
                        productsAdapter.notifyDataSetChanged()
                    } else {

                        val categories: MutableList<String> = mutableListOf()
                        categories.add(this.resources.getString(R.string.product_categories))
                        categories.addAll(products.map { p-> p.category }.distinct())
                        setFavorites()
                        productSpinners = FilterProductSpinners(
                            this,
                            categories,
                            spinner_product_list_categories,
                            spinner_product_list_order,
                            { pos ->

                                onSelectedCategory(pos)
                            },
                            { pos ->

                                onSelectedOrder(pos)
                            },
                            R.layout.simple_spinner_item_blue
                        )
                        productsAdapter.products = products.toMutableList()
                        productsAdapter.notifyDataSetChanged()

                    }
                }
            })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

        model.getAddProductToCartObservable().observe(this, Observer<Any> {

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

        productsAdapter.products = filtered.toMutableList()
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

