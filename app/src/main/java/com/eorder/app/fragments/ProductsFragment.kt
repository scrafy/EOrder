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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eorder.app.R
import com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.app.adapters.fragments.OrderProductAdapter
import com.eorder.app.com.eorder.app.interfaces.IOpenProductCalendar
import com.eorder.app.helpers.FilterProductSpinners
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.*
import com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.app.widgets.AlertDialogInput
import com.eorder.app.widgets.AlertDialogQuestion
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


@RequiresApi(Build.VERSION_CODES.O)
class ProductsFragment : BaseFragment(), IRepaintModel, ISetAdapterListener,
    IShowSnackBarMessage,
    IToolbarSearch {

    private lateinit var model: ProductsViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: OrderProductAdapter = OrderProductAdapter(listOf(), this)
    private  var products: MutableList<Product> = mutableListOf()
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var productSpinners: FilterProductSpinners
    private var filters: MutableMap<String, String?> = mutableMapOf()


    init {

        filters["category"] = null
        filters["order"] = null
        filters["search"] = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        if ( !products.isNullOrEmpty() ){
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
            swipeRefresh_products_fragment,
            resources.getString(R.string.close),
            message
        ).show()
    }


    override fun getSearchFromToolbar(search: String) {
        if (search != "")
            filters["search"] = search
        else
            filters["search"] = null

        applyFilters()
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


        if (product.image != null)
            view.findViewById<ImageView>(R.id.imgView_order_product_list_img_product).setImageBitmap(
                product.image
            )
        else
            LoadImageHelper().setGifLoading(view.findViewById(R.id.imgView_order_product_list_img_product))

        if (!product.amountsByDay.isNullOrEmpty()) {

            view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
                .setImageDrawable(resources.getDrawable(R.drawable.ic_calendario_confirmado))
        }else{
            view.findViewById<ImageView>(R.id.imgView_order_order_product_list_calendar)
                .setImageDrawable(resources.getDrawable(R.drawable.ic_calendario))
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        model = getViewModel()
        init()
        setObservers()

        val catalogId = arguments?.getInt("catalogId")
        val centerId = arguments?.getInt("centerId")
        if (catalogId != null && centerId != null)
            model.getProductsByCatalog(centerId, catalogId)
        else {
            //TODO show snackbar showing message error
        }
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_order_product_list_remove).setOnClickListener {

            if (!product.amountsByDay.isNullOrEmpty()) {

                AlertDialogQuestion(
                    context!!,
                    "Calendar",
                    "If you modify the amount, the product calendar will be reset.\n¿Are you sure you want to modify the amount?",
                    "Modify",
                    "Cancel",
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
                    "Calendar",
                    "If you modify the amount, the product calendar will be reset.\n¿Are you sure you want to modify the amount?",
                    "Modify",
                    "Cancel",
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
                    "Calendar",
                    "If you modify the amount, the product calendar will be reset.\n¿Are you sure you want to modify the amount?",
                    "Modify",
                    "Cancel",
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
        dialog = AlertDialogInput(context!!, "", "", "ADD", "CANCEL", { d, i ->


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
        }, { d, i -> })
        dialog.show()
    }

    private fun setObservers() {

        model.getProductsByCatalogObservable().observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Product>>> {

                products = (it.serverData?.data?.toMutableList() ?: mutableListOf())
                adapter.products = products
                productSpinners = FilterProductSpinners(
                    context!!,
                    products,
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
                setProductCurrentState()
                adapter.notifyDataSetChanged()
                refreshLayout.isRefreshing = false
                loadImages()

            })

        model.getErrorObservable()
            .observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })

    }

    private fun loadImages() {

        LoadImageHelper().loadImage(products)
            .observe(this.activity as LifecycleOwner, Observer<Any> {

                adapter.notifyDataSetChanged()
            })
    }

    private fun init() {

        var menu = mutableMapOf<String, Int>()
        menu["cart_menu"] = R.menu.cart_menu
        (context as ISetActionBar)?.setActionBar(menu, false, true)
        var layout = LinearLayoutManager(this.context)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView = this.view!!.findViewById(R.id.recView_products_fragment_product_list)
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = OrderProductAdapter(
            listOf(),
            this
        )
        recyclerView.adapter = adapter

        refreshLayout = this.view?.findViewById(R.id.swipeRefresh_products_fragment)!!
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        refreshLayout.setOnRefreshListener {

            model.getProductsByCatalog(
                arguments?.getInt("centerId")!!,
                arguments?.getInt("catalogId")!!
            )
        }

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
        }else{
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

        adapter.products = filtered
        adapter.notifyDataSetChanged()
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
