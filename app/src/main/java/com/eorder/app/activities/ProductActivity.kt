package com.eorder.app.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.eorder.app.R
import com.eorder.app.adapters.ProductCatalogListAdapter
import com.eorder.app.adapters.ProductCenterListAdapter
import com.eorder.app.adapters.ProductsAdapter
import com.eorder.app.com.eorder.app.interfaces.IOpenProductCalendar
import com.eorder.app.helpers.FilterProductSpinners
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IFavoriteIconClicked
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.interfaces.IToolbarSearch
import com.eorder.app.viewmodels.ProductViewModel
import com.eorder.app.widgets.AlertDialogInput
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.*
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_seller_product.expandableLayout
import kotlinx.android.synthetic.main.activity_seller_product.expandableLayout2
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ProductActivity : BaseMenuActivity(), IShowSnackBarMessage, IFavoriteIconClicked,
    IRepaintModel, ISetAdapterListener, IToolbarSearch, IOpenProductCalendar {

    private lateinit var model: ProductViewModel
    private lateinit var centerViewPager: ViewPager
    private lateinit var catalogViewPager: ViewPager
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var catalogs: List<Catalog>
    private var categories: MutableList<String> = mutableListOf()
    private var aux: List<Product> = listOf()
    private lateinit var searchProducts: SearchProduct
    private lateinit var pagination: Pagination
    private var currentPage: Int = 1
    private lateinit var centers: List<Center>
    private var products: MutableList<Product> = mutableListOf()
    private var catalogSelected: Catalog? = null
    private var beforeCenterSelected: Center? = null
    private var centerSelected: Center? = null
    private var orderPosition: Int = 0
    private lateinit var productSpinners: FilterProductSpinners
    private var isScrolling: Boolean = false
    private lateinit var manager: LinearLayoutManager
    private var favoriteButtonClicked = false


    init {
        self = this
    }

    companion object {

        var self: ProductActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        model = getViewModel()
        init()
        setListeners()
        setMenuToolbar()
        setObservers()

    }

    override fun getProductsFromShop(): List<Product> {
        return model.getProductsFromShop()
    }

    override fun onResume() {

        if (!products.isNullOrEmpty()) {
            setProductCurrentState()
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

    override fun onFavoriteIconClicked() {

        favoriteButtonClicked = !favoriteButtonClicked

        if ( favoriteButtonClicked ){

            model.getFavoriteProducts(this, searchProducts)
        }
        else {
            paintFavoriteHeart(false)
            products.clear()
            productsAdapter.products = products
            productsAdapter.notifyDataSetChanged()
            currentPage = 1
            model.searchProducts(searchProducts, 1)
        }

    }

    override fun openProductCalendar(product: Product) {

        ProductCalendarActivity.product = product
        var intent = Intent(this, ProductCalendarActivity::class.java)
        startActivity(intent)
    }

    override fun getSearchFromToolbar(search: String) {

        if (search != "")
            searchProducts?.nameProduct = search
        else
            searchProducts?.nameProduct = null

        newSearch()
        searchProducts()
    }


    override fun signOutApp() {
        model.signOutApp(this)
    }

    override fun showMessage(message: String) {
        SnackBar(
            this,
            findViewById<DrawerLayout>(R.id.frameLayout_activity_product_container),
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

        var product = obj as Product
        view.findViewById<Button>(R.id.button_order_product_list_remove).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.alert_dialog_shop_dialog_title_calendar),
                    resources.getString(R.string.shop_activity_dialog_message_reset_calendar),
                    resources.getString(R.string.modify),
                    resources.getString(R.string.cancel),
                    { d, i ->

                        decrementProduct(product)
                    },
                    { d, i -> }
                ).show()
            } else {
                decrementProduct(product)
            }
        }

        view.findViewById<Button>(R.id.button_order_product_list_add).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {
                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.alert_dialog_shop_dialog_title_calendar),
                    resources.getString(R.string.shop_activity_dialog_message_reset_calendar),
                    resources.getString(R.string.modify),
                    resources.getString(R.string.cancel),
                    { d, i ->

                        incrementProduct(product)
                    },
                    { d, i -> }
                ).show()
            } else {
                incrementProduct(product)
            }
        }


        view.findViewById<ImageView>(R.id.imgView_order_product_list_heart).setOnClickListener {
            product.favorite = !product.favorite
            productsAdapter.notifyDataSetChanged()

            model.writeProductsFavorites(
                this,
                product.id
            )
        }

        view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
            .setOnClickListener {

                openProductCalendar(product)
            }

        view.findViewById<TextView>(R.id.textView_order_product_list_amount).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    this,
                    resources.getString(R.string.alert_dialog_shop_dialog_title_calendar),
                    resources.getString(R.string.shop_activity_dialog_message_reset_calendar),
                    resources.getString(R.string.modify),
                    resources.getString(R.string.cancel),
                    { d, i ->

                        modifyAmountOfProduct(product)
                    },
                    { d, i -> }
                ).show()
            } else {
                modifyAmountOfProduct(product)
            }
        }
    }

    private fun decrementProduct(product: Product) {
        if (product.amount > 0)
            product.amount--

        if (product.amount == 0)
            model.removeProductFromShop(product)

        productsAdapter.notifyDataSetChanged()
        product.amountsByDay = null
        showFloatingButton()
    }

    private fun searchProducts() {

        model.searchProducts(searchProducts as SearchProduct, currentPage)

    }


    private fun incrementProduct(product: Product) {

        if (model.isPossibleAddProduct(
                product,
                beforeCenterSelected as Center,
                centerSelected as Center
            )
        ) {

            addProduct(product)

        } else {
            addProductMessage {

                addProduct(product)
            }
        }

    }

    private fun addProduct(product: Product) {

        addSellerToOrder(catalogSelected as Catalog)
        addCenterToOrder(centerSelected as Center)
        product.amount++
        model.addProductToShop(product)
        productsAdapter.notifyDataSetChanged()
        product.amountsByDay = null
        showFloatingButton()
    }

    private fun paintFavoriteHeart( full:Boolean ){

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        val menuItem = toolbar?.menu?.findItem(R.id.item_menu_product_list_favorite)
        if (full)
            menuItem?.setIcon(R.drawable.ic_heart)
        else
            menuItem?.setIcon(R.drawable.ic_corazon_outline_blue)
    }

    private fun addAmountOfProduct(product: Product) {

        addSellerToOrder(catalogSelected as Catalog)
        addCenterToOrder(centerSelected as Center)
        var dialog: AlertDialogInput? = null
        dialog = AlertDialogInput(
            this,
            "",
            "",
            resources.getString(R.string.add),
            resources.getString(R.string.cancel),
            { d, i ->

                if (dialog?.input?.text.isNullOrEmpty()) {
                    product.amount = 0
                } else {
                    product.amount = Integer(dialog?.input?.text.toString()).toInt()
                }
                if (product.amount == 0)
                    model.removeProductFromShop(product)
                else
                    model.addProductToShop(product)


                product.amountsByDay = null
                showFloatingButton()
                productsAdapter.notifyDataSetChanged()
            },
            { d, i -> })
        dialog.show()
    }

    private fun modifyAmountOfProduct(product: Product) {

        if (model.isPossibleAddProduct(
                product,
                beforeCenterSelected as Center,
                centerSelected as Center
            )
        ) {

            addAmountOfProduct(product)
        } else {
            addProductMessage {

                addAmountOfProduct(product)

            }
        }
    }


    private fun repaintCatalogList(view: View, obj: Any?) {

        val catalog = obj as Catalog

        try {
            Glide.with(this).load(catalog.imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_catalogs_list_image))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_catalogs_list_image))
        }


        view.findViewById<TextView>(R.id.textView_catalogs_list_provider_name).text =
            catalog.sellerName
    }

    private fun repaintCenterList(view: View, obj: Any?) {

        val center = obj as Center

        try {
            Glide.with(this).load(center.imageUrl).circleCrop()
                .into(view.findViewById<ImageView>(R.id.imgView_center_list_image))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_center_list_image))
        }

        view.findViewById<TextView>(R.id.textView_center_list_center_name).text =
            center.name

    }


    private fun repaintProductList(view: View, model: Any?) {

        var product = model as Product
        var amountView = view.findViewById<TextView>(R.id.textView_order_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_order_product_list_heart)


        view.findViewById<TextView>(R.id.textView_order_product_list_name).text = product.name
        view.findViewById<TextView>(R.id.textView_order_product_list_category).text =
            product.category
        view.findViewById<TextView>(R.id.textView_order_product_list_price).text =
            if (product.price == 0F) "" else product.price.toString() + "€"
        view.findViewById<TextView>(R.id.textView_order_product_list_amount).text =
            product.amount.toString()


        if (product.amount == 0) {
            amountView.background =
                this.getDrawable(R.drawable.shape_amount_zero_products)
        } else {
            amountView.background = this.getDrawable(R.drawable.shape_amount_products)
        }

        if (product.favorite) {
            heart.setBackgroundResource(R.drawable.ic_corazon)

        } else {
            heart.setBackgroundResource(R.drawable.ic_corazon_outline)

        }
        amountView.text = product.amount.toString()


        try {
            Glide.with(this).load(product.imageUrl)
                .into(view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product))
        } catch (ex: Exception) {
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product))
        }


        if (!product.amountsByDay.isNullOrEmpty()) {

            view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
                .setImageDrawable(resources.getDrawable(R.drawable.ic_calendario_confirmado))
        } else {
            view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
                .setImageDrawable(resources.getDrawable(R.drawable.ic_calendario))
        }

    }

    private fun orderProducts(position: Int, _products: List<Product>? = null) {

        if (position == 0) {
            if (_products == null) {
                this.products = this.products.sortedBy { p -> p.name }.toMutableList()
            } else {
                aux = _products?.sortedBy { p -> p.name }
            }
        } else if (position == 1) {
            if (_products == null) {
                this.products = this.products.sortedByDescending { p -> p.name }.toMutableList()
            } else {
                aux = _products?.sortedByDescending { p -> p.name }
            }
        } else if (position == 2) {
            if (_products == null) {
                this.products = this.products.sortedBy { p -> p.price }.toMutableList()
            } else {
                aux = _products?.sortedBy { p -> p.price }
            }
        } else if (position == 3) {
            if (_products == null) {
                this.products = this.products.sortedByDescending { p -> p.price }.toMutableList()
            } else {
                aux = _products?.sortedByDescending { p -> p.price }
            }

        }

    }

    private fun init() {

        catalogViewPager = findViewById(R.id.viewPager_activity_product_catalogs)
        centerViewPager = findViewById(R.id.viewPager_activity_product_centers)

        recyclerView = findViewById(R.id.recView_product_activity_product_list)
        recyclerView.itemAnimator = DefaultItemAnimator()
        productsAdapter = ProductsAdapter()
        recyclerView.adapter = productsAdapter
        manager = LinearLayoutManager(this).apply { isAutoMeasureEnabled = false }
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        model.getCenters()
    }

    private fun newSearch() {

        currentPage = 1
        productsAdapter.resetProducts()
        products = mutableListOf()
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

    private fun addProductMessage(callback: () -> Unit) {

        AlertDialogQuestion(self?.getContext()!!, resources.getString(R.string.catalogs),
            resources.getString(R.string.product_activity_add_product),
            resources.getString(R.string.add),
            resources.getString(R.string.no_add),

            { i, t ->

                model.resetAmountOfProducts()
                model.cleanShop()
                beforeCenterSelected = centerSelected
                callback()

            },
            { i, t ->


            }

        ).show()
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

                catalogSelected = catalogs[position]
                searchProducts?.catalogId = (catalogSelected as Catalog).id
                model.getCategories((catalogSelected as Catalog).id)
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

                centerSelected = centers[position]

                if (model.isShopEmpty())
                    beforeCenterSelected = centerSelected

                model.getCatalogByCenter((centerSelected as Center).id)
            }

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)
                var currentItems = manager.childCount
                var totalItems = manager.itemCount
                var scrollOutItems = manager.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems) && !favoriteButtonClicked) {

                    isScrolling = false
                    if (currentPage < pagination.totalPages) {
                        currentPage += 1
                        imgView_activity_product_pedidoe_loading.visibility = View.VISIBLE
                        searchProducts()
                    }
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                    isScrolling = true
                }
            }
        })
    }


    private fun setObservers() {

        model.searchProductsResult.observe(
            this,
            Observer<ServerResponse<List<Product>>> {

                imgView_activity_product_pedidoe_loading.visibility = View.INVISIBLE
                if (it.ServerData?.Data.isNullOrEmpty()) {

                    AlertDialogOk(
                        this,
                        resources.getString(R.string.productos),
                        resources.getString(R.string.products_fragment_no_products_search_message),
                        resources.getString(R.string.ok)
                    ) { d, i -> }.show()
                } else {

                    pagination = it.ServerData?.PaginationData!!
                    aux = it.ServerData?.Data!!
                    orderProducts(orderPosition, aux)
                    val oldSize = products.size
                    products.addAll(aux)
                    setProductCurrentState()
                    productsAdapter.products = products
                    productsAdapter.notifyItemRangeInserted(oldSize, aux.size)

                }

            })

        model.centersResult
            .observe(this, Observer<ServerResponse<List<Center>>> {

                centers = it.ServerData?.Data ?: listOf()
                centerSelected = centers.first { x ->
                    x.id == intent.getIntExtra(
                        "centerId",
                        centers.first().id
                    )
                }
                beforeCenterSelected = centerSelected
                addCenterToOrder(centerSelected as Center)
                centerViewPager.adapter = ProductCenterListAdapter(this, centers)
                centerViewPager.adapter?.notifyDataSetChanged()
                addDots(centers.size, linearLayout_activity_product_dots_centers)
                (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_centers).getChildAt(
                    0
                ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))
                val index =
                    centers.indexOf(centers.firstOrNull { s -> s.id == (centerSelected as Center).id }
                        ?: centers[0])
                if (index == 0) {
                    (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_centers).getChildAt(
                        0
                    ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))
                    model.getCatalogByCenter(centers[0].id)

                } else
                    centerViewPager.currentItem = index
            })

        model.catalogsResult
            .observe(this, Observer<ServerResponse<List<Catalog>>> {

                catalogs = it.ServerData?.Data ?: listOf()
                if (catalogs.isEmpty()) {
                    AlertDialogOk(
                        this,
                        resources.getString(R.string.product_activity_no_catalog_dialog_title),
                        String.format(
                            resources.getString(R.string.product_activity_no_catalog_dialog_message),
                            centers.find { c -> c.id == (centerSelected as Center).id }?.name
                        ),
                        resources.getString(R.string.ok)

                    ) { d, i -> }.show()
                } else {
                    linearLayout_activity_product_dots_catalogs.removeAllViews()
                    addDots(catalogs.size, linearLayout_activity_product_dots_catalogs)
                    catalogViewPager.adapter = ProductCatalogListAdapter(this, catalogs)
                    catalogViewPager.adapter?.notifyDataSetChanged()
                    (findViewById<LinearLayout>(R.id.linearLayout_activity_product_dots_catalogs).getChildAt(
                        0
                    ) as TextView).setTextColor(resources.getColor(R.color.primaryText, null))
                    catalogSelected = catalogs[0]
                    searchProducts = SearchProduct(
                        (centerSelected as Center).id,
                        (catalogSelected as Catalog).id,
                        null,
                        null
                    )
                    model.getCategories((catalogSelected as Catalog).id)
                }

            })

        model.categoriesResult.observe(this, Observer<ServerResponse<List<Category>>> {

            val result = it.ServerData?.Data!!
            loadSpinnersData(result.map { c -> c.categoryName })

        })

        model.getErrorObservable().observe(this, Observer<Throwable> { ex ->

            model.getManagerExceptionService().manageException(this, ex)

        })

        model.getAddProductToCartObservable().observe(this, Observer<Any> {

            this.showFloatingButton()
        })

        model.getFavoriteProductsResult.observe(
            this as LifecycleOwner,
            Observer<ServerResponse<List<Product>>> {

                if (it.ServerData?.Data.isNullOrEmpty()) {

                    AlertDialogOk(
                        this,
                        resources.getString(R.string.productos),
                        resources.getString(R.string.products_fragment_no_favorite_products_message),
                        resources.getString(R.string.ok)
                    ) { d, i ->  favoriteButtonClicked = false}.show()

                } else {
                    paintFavoriteHeart(true)
                    favoriteButtonClicked = true
                    products = it.ServerData?.Data!! as MutableList<Product>
                    setProductCurrentState()
                    productsAdapter.products = products
                    productsAdapter.notifyDataSetChanged()

                }

            })

    }

    private fun addCenterToOrder(center: Center) {

        model.addCenterToOrder(center.id, center.name, center.imageUrl, center.buyerId)
    }

    private fun addSellerToOrder(catalog: Catalog) {

        model.addSellerToOrder(catalog.sellerId, catalog.sellerName, catalog.primaryCode)
    }

    private fun onSelectedCategory(position: Int) {

        if (searchProducts != null) {
            newSearch()
            if (position == 0)
                searchProducts?.category = null
            else
                searchProducts?.category = categories[position]

            searchProducts()
        }

    }

    private fun setProductCurrentState() {

        if (model.getProductsFromShop().isNotEmpty()) {

            val productsShop = model.getProductsFromShop()
            productsShop.forEach { p ->

                val found = this.products.firstOrNull { _p -> _p.id == p.id }
                if (found != null)
                    this.products[this.products.indexOf(found)] = p

            }
            val products = this.products.filter { p -> p.amount > 0 }
            products.forEach { p ->
                if (productsShop.find { _p -> _p.id == p.id } == null) {
                    p.amount = 0
                }
            }
        } else {
            products.filter { it.amount > 0 }.forEach { it.amount = 0 }
            products.filter { !it.amountsByDay.isNullOrEmpty() }.forEach { it.amountsByDay = null }
        }

        val favorites = model.loadFavoritesProducts(this)
        this.products.forEach { p -> p.favorite = false }
        if (favorites != null)
            this.products.filter { p ->
                p.id in favorites
            }.forEach { p -> p.favorite = true }
    }

    private fun onSelectedOrder(position: Int) {

        orderProducts(position)
        orderPosition = position
        productsAdapter.products = products
        productsAdapter.notifyDataSetChanged()
    }

    private fun loadSpinnersData(_categories: List<String>) {

        categories.clear()
        categories.add(this.resources.getString(R.string.product_categories))
        categories.addAll(_categories)
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
            R.layout.simple_spinner_item
        )
    }


}

