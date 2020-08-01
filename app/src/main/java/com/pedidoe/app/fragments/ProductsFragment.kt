package com.pedidoe.app.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedidoe.app.R
import com.pedidoe.app.activities.BaseFloatingButtonActivity
import com.pedidoe.app.adapters.fragments.OrderProductAdapter
import com.pedidoe.app.com.eorder.app.interfaces.IOpenProductCalendar
import com.pedidoe.app.helpers.FilterProductSpinners
import com.pedidoe.app.helpers.LoadImageHelper
import com.pedidoe.app.interfaces.*
import com.pedidoe.app.viewmodels.fragments.ProductsFragmentViewModel
import com.pedidoe.app.widgets.AlertDialogInput
import com.pedidoe.app.widgets.AlertDialogOk
import com.pedidoe.app.widgets.AlertDialogQuestion
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.models.*
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ProductsFragment : BaseFragment(), IRepaintModel, ISetAdapterListener,
    IShowSnackBarMessage, IFavoriteIconClicked,
    IToolbarSearch {


    private lateinit var model: ProductsFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: OrderProductAdapter = OrderProductAdapter(this)
    private var products: MutableList<Product> = mutableListOf()
    private var aux: List<Product> = listOf()
    private lateinit var productSpinners: FilterProductSpinners
    private var categories: MutableList<String> = mutableListOf()
    private lateinit var pagination: Pagination
    private var currentPage: Int = 1
    private lateinit var searchProducts: SearchProduct
    private var orderPosition: Int = 0
    private lateinit var center: OrderCenterInfo
    private lateinit var seller: OrderSellerInfo
    private var isScrolling: Boolean = false
    private lateinit var manager: LinearLayoutManager
    private var favoriteButtonClicked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        model.getOrder().center = center
        model.getOrder().seller = seller
        if (!products.isNullOrEmpty()) {
            setProductCurrentState()
            adapter.notifyDataSetChanged()
        }
        (context as BaseFloatingButtonActivity).showFloatingButton()
    }

    override fun showMessage(message: String) {

        SnackBar(
            context!!,
            frameLayout_products_fragment_container,
            resources.getString(R.string.close),
            message
        ).show()
    }


    override fun getSearchFromToolbar(search: String) {
        if (search != "")
            searchProducts.nameProduct = search
        else
            searchProducts.nameProduct = null

        newSearch()
        searchProducts()
    }

    override fun onFavoriteIconClicked() {

        favoriteButtonClicked = !favoriteButtonClicked

        if (favoriteButtonClicked) {

            model.getFavoriteProducts(context!!, searchProducts)
        } else {
            paintFavoriteHeart(false)
            products.clear()
            adapter.products = products
            adapter.notifyDataSetChanged()
            currentPage = 1
            model.searchProducts(searchProducts, 1)
        }

    }


    override fun repaintModel(view: View, model: Any?) {

        var product = model as Product
        var amountView = view.findViewById<TextView>(R.id.textView_order_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_order_product_list_heart)


        view.findViewById<TextView>(R.id.textView_order_product_list_name).text = product.name

        view.findViewById<TextView>(R.id.textView_order_product_list_price).text =
            if (product.price == 0F) "" else product.price.toString() + "€"
        view.findViewById<TextView>(R.id.textView_order_product_list_amount).text =
            product.amount.toString()


        if (product.amount == 0) {
            amountView.background =
                context?.getDrawable(R.drawable.shape_amount_zero_products)
        } else {
            amountView.background = context?.getDrawable(R.drawable.shape_amount_products)
        }

        if (product.favorite) {
            heart.setBackgroundResource(R.drawable.ic_corazon)

        } else {
            heart.setBackgroundResource(R.drawable.ic_corazon_outline)

        }
        amountView.text = product.amount.toString()


        try {
            Glide.with(context!!).load(product.imageUrl)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        model = getViewModel()
        setObservers()
        init()
        setListeners()
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_order_product_list_remove).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    context!!,
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
                    context!!,
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
            adapter.notifyDataSetChanged()

            if (product.favorite)
                model.saveProductAsFavorite(product.id)
            else {
                model.deleteProductFromFavorites(product.id)

                if ( favoriteButtonClicked ){
                    val index = products.indexOf(products.find { p -> p.id == product.id })
                    products.removeAt(index)
                    adapter.notifyItemRemoved(index)
                    if ( products.isNullOrEmpty() ){
                        onFavoriteIconClicked()
                    }
                }
            }
        }

        view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
            .setOnClickListener {

                (context as IOpenProductCalendar).openProductCalendar(product)
            }

        view.findViewById<TextView>(R.id.textView_order_product_list_amount).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    context!!,
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

    override fun onDestroy() {

        super.onDestroy()
        var map = mutableMapOf<String, Int>()
        map["main_menu"] = R.menu.main_menu
        (context as ISetActionBar)?.setActionBar(map, false, true)
    }

    private fun decrementProduct(product: Product) {
        if (product.amount > 0)
            product.amount--

        if (product.amount == 0)
            model.removeProductFromShop(product)

        adapter.notifyDataSetChanged()
        product.amountsByDay = null
        (context as BaseFloatingButtonActivity).showFloatingButton()
    }

    private fun incrementProduct(product: Product) {

        product.amount++
        model.addProductToShop(product)
        adapter.notifyDataSetChanged()
        product.amountsByDay = null
        (context as BaseFloatingButtonActivity).showFloatingButton()
    }

    private fun modifyAmountOfProduct(product: Product) {

        var dialog: AlertDialogInput? = null
        dialog = AlertDialogInput(
            context!!,
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
                (context as BaseFloatingButtonActivity).showFloatingButton()
                adapter.notifyDataSetChanged()
            },
            { d, i -> })
        dialog.show()
    }

    private fun setObservers() {

        model.searchProductsResult.observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Product>>> {

                imgView_products_fragment_pedidoe_loading.visibility = View.INVISIBLE
                if (!it.ServerData?.Data.isNullOrEmpty()) {

                    pagination = it.ServerData?.PaginationData!!
                    aux = it.ServerData?.Data!!
                    orderProducts(orderPosition, aux)
                    val oldSize = products.size
                    products.addAll(aux)
                    setProductCurrentState()
                    adapter.products = products
                    adapter.notifyItemRangeInserted(oldSize, aux.size)
                }

            })

        model.getFavoriteProductsIdsResult.observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Int>>> {

                val ids = it.ServerData?.Data

                if (!ids.isNullOrEmpty()) {
                    this.products.forEach { p -> p.favorite = false }
                    this.products.filter { p ->
                        p.id in ids
                    }.forEach { p -> p.favorite = true }
                }
                adapter.products = products
                adapter.notifyDataSetChanged()

            })

        model.deleteProductFromFavoriteListResult.observe(
            this.activity as LifecycleOwner,
            Observer<Any> {

            })

        model.getFavoriteProductsResult.observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Product>>> {

                if (it.ServerData?.Data.isNullOrEmpty()) {

                    AlertDialogOk(
                        context!!,
                        resources.getString(R.string.productos),
                        resources.getString(R.string.products_fragment_no_favorite_products_message),
                        resources.getString(R.string.ok)
                    ) { d, i ->  favoriteButtonClicked = false;  paintFavoriteHeart(favoriteButtonClicked)}.show()

                } else {
                    favoriteButtonClicked = true
                    paintFavoriteHeart(favoriteButtonClicked)
                    products = it.ServerData?.Data!! as MutableList<Product>
                    setProductCurrentState()
                }

            })

        model.getErrorObservable()
            .observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->


                model.getManagerExceptionService().manageException(this.context!!, ex)
            })

    }

    private fun loadSpinnersData(_categories: List<String>) {

        categories.add(context!!.resources.getString(R.string.product_categories))
        categories.addAll(_categories)
        productSpinners = FilterProductSpinners(
            context!!,
            categories,
            spinner_products_fragment_list_categories,
            spinner_product_products_fragment_list_order,
            { pos ->

                onSelectedCategory(pos)
            },
            { pos ->

                onSelectedOrder(pos)
            },
            R.layout.simple_spinner_item
        )
    }

    private fun init() {

        center = model.getOrder().center
        seller = model.getOrder().seller
        val Data = Gson.Create().fromJson(
            arguments!!.getString("data"),
            DataProductFragment::class.java
        )
        searchProducts = SearchProduct(Data.centerId, Data.catalogId)
        loadSpinnersData(
            Data.categories.map { it.categoryName }
        )
        var menu = mutableMapOf<String, Int>()
        menu["search_menu"] = R.menu.search_menu
        (context as ISetActionBar)?.setActionBar(menu, false, true)
        manager = LinearLayoutManager(this.context).apply { isAutoMeasureEnabled = false }
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView = this.view!!.findViewById(R.id.recView_products_fragment_product_list)
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = OrderProductAdapter(

            this
        )
        recyclerView.adapter = adapter
        spinner_products_fragment_list_categories.setSelection(categories.indexOf(Data.categorySelected.categoryName))
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

        model.getFavoriteProductsIds()

    }

    private fun searchProducts() {

        model.searchProducts(searchProducts, currentPage)

    }

    private fun paintFavoriteHeart(full: Boolean) {

        val toolbar = this.activity!!.findViewById<Toolbar>(R.id.toolbar)
        val menuItem = toolbar?.menu?.findItem(R.id.item_menu_product_list_favorite)
        if (full)
            menuItem?.setIcon(R.drawable.ic_heart)
        else
            menuItem?.setIcon(R.drawable.ic_corazon_outline_blue)
    }

    private fun onSelectedCategory(position: Int) {

        favoriteButtonClicked = false
        paintFavoriteHeart(favoriteButtonClicked)
        newSearch()
        if (position == 0)
            searchProducts.category = null
        else
            searchProducts.category = categories[position]

        searchProducts()
    }

    private fun onSelectedOrder(position: Int) {

        orderProducts(position)
        orderPosition = position
        adapter.products = products
        adapter.notifyDataSetChanged()

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

    private fun newSearch() {
        currentPage = 1
        adapter.resetProducts()
        products = mutableListOf()
    }

    private fun setListeners() {

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
                        imgView_products_fragment_pedidoe_loading.visibility = View.VISIBLE
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

}
