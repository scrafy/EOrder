package com.eorder.app.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.adapters.fragments.OrderProductAdapter
import com.eorder.app.com.eorder.app.interfaces.IOpenProductCalendar
import com.eorder.app.helpers.FilterProductSpinners
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.*
import com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.app.widgets.AlertDialogInput
import com.eorder.app.widgets.AlertDialogOk
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.factories.Gson
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Pagination
import com.eorder.domain.models.Product
import com.eorder.domain.models.SearchProduct
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.bumptech.glide.Glide


@RequiresApi(Build.VERSION_CODES.O)
class ProductsFragment : BaseFragment(), IRepaintModel, ISetAdapterListener,
    IShowSnackBarMessage,
    IToolbarSearch {

    private lateinit var model: ProductsViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: OrderProductAdapter = OrderProductAdapter(this)
    private var products: MutableList<Product> = mutableListOf()
    private lateinit var productSpinners: FilterProductSpinners
    private var categories: MutableList<String> = mutableListOf()
    private lateinit var pagination: Pagination
    private var currentPage: Int = 1
    private lateinit var searchProducts: SearchProduct


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (!products.isNullOrEmpty()) {
            setProductCurrentState()
            adapter.notifyDataSetChanged()
        }
        (context as IRepaintShopIcon).repaintShopIcon()
        (context as BaseFloatingButtonActivity).hideFloatingButton()
    }

    override fun showMessage(message: String) {
        editText_activity_main_code_input.text.clear()
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

    override fun repaintModel(view: View, model: Any?) {

        var product = model as Product
        var amountView = view.findViewById<TextView>(R.id.textView_order_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_order_product_list_heart)


        view.findViewById<TextView>(R.id.textView_order_product_list_name).text = product.name
        view.findViewById<TextView>(R.id.textView_order_product_list_category).text =
            product.category
        view.findViewById<TextView>(R.id.textView_order_product_list_price).text =
            if (product.price == 0F) "N/A" else product.price.toString() + "€"
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


        Glide.with(context!!).load(product.imageUrl).into(view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product))

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

            model.writeProductsFavorites(
                this.context!!,
                product.id
            )

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
        (context as IRepaintShopIcon).repaintShopIcon()
    }

    private fun incrementProduct(product: Product) {

        product.amount++
        model.addProductToShop(product)
        adapter.notifyDataSetChanged()
        product.amountsByDay = null
        (context as IRepaintShopIcon).repaintShopIcon()
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
                (context as IRepaintShopIcon).repaintShopIcon()
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
                if (it.serverData?.data.isNullOrEmpty()) {
                    hideLoadMoreProductsButton()
                    AlertDialogOk(
                        context!!,
                        resources.getString(R.string.productos),
                        resources.getString(R.string.products_fragment_no_products_search_message),
                        resources.getString(R.string.ok)
                    ) { d, i -> }.show()
                } else {
                    showLoadMoreProductsButton()
                    pagination = it.serverData?.paginationData!!
                    products.addAll(it.serverData?.data!!)
                    setProductCurrentState()
                    adapter.products = products
                    spinner_product_products_fragment_list_order.setSelection( spinner_product_products_fragment_list_order.selectedItemPosition )

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

        val data = Gson.Create().fromJson(
            arguments!!.getString("data"),
            CategoriesFragment.DataProductFragment::class.java
        )
        searchProducts = SearchProduct(data.centerId, data.catalogId)
        loadSpinnersData(
            data.categories.map { it.categoryName }

        )
        var menu = mutableMapOf<String, Int>()
        menu["cart_menu"] = R.menu.cart_menu
        (context as ISetActionBar)?.setActionBar(menu, false, true)
        var layout = LinearLayoutManager(this.context).apply { isAutoMeasureEnabled = false }
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView = this.view!!.findViewById(R.id.recView_products_fragment_product_list)
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = OrderProductAdapter(

            this
        )
        recyclerView.adapter = adapter
        spinner_products_fragment_list_categories.setSelection(categories.indexOf(data.categorySelected.categoryName))


    }

    private fun setProductCurrentState() {

        if (model.getProductsFromShop().isNotEmpty()) {

            val productsShop = model.getProductsFromShop()
            this.products.forEach { p ->

                val found = productsShop.firstOrNull { _p -> _p.id === p.id }
                if (found != null) {

                    p.amount = found.amount
                    p.favorite = found.favorite
                    p.amountsByDay = found.amountsByDay
                    model.removeProductFromShop(found)
                    model.addProductToShop(p)

                }

            }
        } else {
            products.filter { it.amount > 0 }.forEach { it.amount = 0 }
            products.filter { !it.amountsByDay.isNullOrEmpty() }.forEach { it.amountsByDay = null }
        }

        val favorites = model.loadFavoritesProducts(context)
        this.products.forEach { p -> p.favorite = false }
        if (favorites != null)
            this.products.filter { p ->
                p.id in favorites
            }.forEach { p -> p.favorite = true }
    }

    private fun searchProducts() {
        hideLoadMoreProductsButton()
        model.searchProducts(searchProducts, currentPage)
        imgView_products_fragment_pedidoe_loading.visibility = View.VISIBLE
    }

    private fun onSelectedCategory(position: Int) {
        newSearch()
        if (position == 0)
            searchProducts.category = null
        else
            searchProducts.category = categories[position]

        searchProducts()
    }

    private fun onSelectedOrder(position: Int) {

        when (position) {

            0 -> {
                adapter.products = adapter.products.sortedBy { p -> p.name }.toMutableList()

            }
            1 -> {
                adapter.products = adapter.products.sortedByDescending { p -> p.name }.toMutableList()

            }
            2 -> {
                adapter.products = adapter.products.sortedBy { p -> p.price }.toMutableList()
            }
            3 -> {
                adapter.products = adapter.products.sortedByDescending { p -> p.price }.toMutableList()
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun newSearch(){
        currentPage = 1
        adapter.resetProducts()
        products = mutableListOf()
    }

    private fun setListeners() {

        button_products_fragment_load_more_products.setOnClickListener {
            if (currentPage < pagination.numPages) {
                searchProducts()
                currentPage++
            } else {
                hideLoadMoreProductsButton()
                AlertDialogOk(
                    context!!,
                    resources.getString(R.string.productos),
                    resources.getString(R.string.products_fragment_no_products_message),
                    resources.getString(R.string.ok)
                ) { d, i -> }.show()
            }

        }
    }

    private fun hideLoadMoreProductsButton() {

        button_products_fragment_load_more_products.visibility = View.INVISIBLE
    }

    private fun showLoadMoreProductsButton() {
        button_products_fragment_load_more_products.visibility = View.VISIBLE
    }
}
