package com.eorder.app.activities

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.eorder.app.R
import com.eorder.app.adapters.ProductCatalogListAdapter
import com.eorder.app.adapters.ProductCenterListAdapter
import com.eorder.app.adapters.ProductsAdapter
import com.eorder.app.helpers.FilterProductSpinners
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IToolbarSearch
import com.eorder.app.viewmodels.ProductViewModel
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.Center
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_seller_product.expandableLayout
import kotlinx.android.synthetic.main.activity_seller_product.expandableLayout2
import kotlinx.android.synthetic.main.products_fragment.spinner_product_list_categories
import kotlinx.android.synthetic.main.products_fragment.spinner_product_list_order
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProductActivity : BaseMenuActivity(), IShowSnackBarMessage,
    IRepaintModel, ISetAdapterListener, IToolbarSearch {

    private lateinit var model: ProductViewModel
    private lateinit var centerViewPager: ViewPager
    private lateinit var catalogViewPager: ViewPager
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var catalogs: List<Catalog>
    private lateinit var centers: List<Center>
    private var products: List<Product> = listOf()
    private var catalogSelected: Int = 0
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
        setContentView(R.layout.activity_product)
        model = getViewModel()
        init()
        setListeners()
        setMenuToolbar()
        setObservers()
        model.getCenters()
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


        if (model is Catalog)
            repaintCatalogList(view, model)

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

            model.addProductToShop(this, obj)
        }

        view.findViewById<TextView>(R.id.textView_products_list_add).setOnClickListener {

            model.addProductToShop(this, obj)
        }
    }


    private fun repaintCatalogList(view: View, obj: Any?) {

        val catalog = obj as Catalog


        LoadImageHelper().loadImage(
            catalog.imageUrl,
            view.findViewById(R.id.imgView_catalogs_list_image),
            false
        )

        view.findViewById<TextView>(R.id.textView_catalogs_list_provider_name).text =
            catalog.sellerName
    }

    private fun repaintCenterList(view: View, obj: Any?) {

        val center = obj as Center

        LoadImageHelper().loadImage(
            center.imageUrl,
            view.findViewById(R.id.imgView_center_list_image),
            true
        )

        view.findViewById<TextView>(R.id.textView_center_list_center_name).text =
            center.center_name

    }

    private fun loadImages() {

        LoadImageHelper().loadImage(products.filter { p -> p.image == null })
            .observe(this as LifecycleOwner, Observer<Any> {

                productsAdapter.notifyDataSetChanged()
            })
    }


    private fun repaintProductList(view: View, obj: Any?) {

        val product = (obj as Product)
        view.findViewById<TextView>(R.id.textView_products_list_product_name).text = product.name
        view.findViewById<TextView>(R.id.textView_products_list_price).text = "${product.price}€"
        view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
            .setBackgroundResource(R.drawable.ic_corazon)

        if (product.image != null)
            view.findViewById<ImageView>(R.id.imgView_products_list_image_product).setImageBitmap(
                product.image
            )
        else
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_products_list_image_product))

        if (product.favorite) {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon)

        } else {
            view.findViewById<ImageView>(R.id.imgView_products_list_image_heart)
                .setBackgroundResource(R.drawable.ic_corazon_outline)

        }
    }

    private fun init() {

        catalogViewPager = findViewById(R.id.viewPager_activity_product_catalogs)
        centerViewPager = findViewById(R.id.viewPager_activity_product_centers)

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

        textView_activity_product_select_center.setOnClickListener {

            expandableLayout.toggle()

            if (expandableLayout.isExpanded) {
                textView_activity_product_select_center.text =
                    "${textView_activity_product_select_center.text.substring(
                        0,
                        textView_activity_product_select_center.text.length - 1
                    )}▼"
            } else {
                textView_activity_product_select_center.text =
                    "${textView_activity_product_select_center.text.substring(
                        0,
                        textView_activity_product_select_center.text.length - 1
                    )}▲"
            }
        }

        textView_activity_product_select_catalogs.setOnClickListener {

            expandableLayout2.toggle()
            if (expandableLayout2.isExpanded) {
                textView_activity_product_select_catalogs.text =
                    "${textView_activity_product_select_catalogs.text.substring(
                        0,
                        textView_activity_product_select_catalogs.text.length - 1
                    )}▼"
            } else {
                textView_activity_product_select_catalogs.text =
                    "${textView_activity_product_select_catalogs.text.substring(
                        0,
                        textView_activity_product_select_catalogs.text.length - 1
                    )}▲"
            }
        }

        catalogViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_catalogs).children.iterator()
                    .forEach { v ->
                        (v as TextView).setTextColor(
                            resources.getColor(
                                R.color.color_transparent,
                                null
                            )
                        )
                    }

                (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_catalogs).getChildAt(
                    position
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))

                catalogSelected = catalogs[position].id
                model.getProductsByCatalog(centerSelected, catalogSelected)

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

                findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_centers).children.iterator()
                    .forEach { v ->
                        (v as TextView).setTextColor(
                            resources.getColor(
                                R.color.color_transparent,
                                null
                            )
                        )
                    }

                (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_centers).getChildAt(
                    position
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))

                centerSelected = centers[position].id
                model.getProductsByCatalog(centerSelected, catalogSelected)

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

        model.getCentersResultObservable()
            .observe(this, Observer<ServerResponse<List<Center>>> {

                centers = it.serverData?.data ?: listOf()
                centerSelected = intent.getIntExtra("centerId", 0)
                centerViewPager.adapter = ProductCenterListAdapter(this, centers)
                centerViewPager.adapter?.notifyDataSetChanged()
                addDots(centers.size, linearLayout_activity_product_dots_centers)
                (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_centers).getChildAt(
                    0
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))
                val index = centers.indexOf(centers.firstOrNull { s -> s.id == centerSelected } ?: centers[0])
                if (index == 0) {
                    (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_centers).getChildAt(
                        0
                    ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))

                } else
                    centerViewPager.currentItem = index

                model.getCatalogByCenter(centers[0].id)


            })

        model.getCatalogsResultObservable()
            .observe(this, Observer<ServerResponse<List<Catalog>>> {

                catalogs = it.serverData?.data ?: listOf()
                if (catalogs.isEmpty()) {
                    AlertDialogOk(
                        this,
                        "No catalogs",
                        "${centers.find { c -> c.id == centerSelected }?.center_name} does not have any catalog setted",
                        "OK"

                    ) { d, i -> }.show()
                } else {
                    addDots(catalogs.size, linearLayout_activity_product_dots_catalogs)
                    catalogViewPager.adapter = ProductCatalogListAdapter(this, catalogs)
                    catalogViewPager.adapter?.notifyDataSetChanged()
                    (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_catalogs).getChildAt(
                        0
                    ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))
                    catalogSelected = catalogs[0].id
                    model.getProductsByCatalog(centerSelected, catalogSelected)
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
                loadImages()
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

