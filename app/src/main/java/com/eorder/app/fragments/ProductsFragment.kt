package com.eorder.app.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eorder.app.adapters.fragments.ProductAdapter
import com.eorder.app.interfaces.IRepaintShopIcon
import com.eorder.app.interfaces.*
import com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.application.extensions.toBitmap
import com.eorder.application.models.UrlLoadedImage
import com.eorder.domain.models.Product
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import pl.droidsonroids.gif.GifDrawable
import com.eorder.app.R
import com.eorder.app.activities.BaseFloatingButtonActivity
import com.eorder.application.interfaces.IShowSnackBarMessage
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class ProductsFragment : BaseFragment(), IRepaintModel, ISetAdapterListener,
    IShowSnackBarMessage,
    IToolbarSearch {

    private lateinit var model: ProductsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private var products: List<Product> = listOf()
    private lateinit var refreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
        (context as IRepaintShopIcon).repaintShopIcon()
        (context as BaseFloatingButtonActivity).hideFloatingButton()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun getSearchFromToolbar(search: String) {
        adapter.products =
            products.filter { p -> p.name.toLowerCase().contains(search.toLowerCase()) }
        adapter.notifyDataSetChanged()
    }

    override fun repaintModel(view: View, model: Any?) {

        var product = model as Product
        var amountView = view.findViewById<TextView>(R.id.textView_product_list_amount)
        var heart = view.findViewById<ImageView>(R.id.imgView_product_list_heart)


        view.findViewById<TextView>(R.id.textView_product_list_name).setText(product.name)
        view.findViewById<TextView>(R.id.textView_product_list_category).text = product.category
        view.findViewById<TextView>(R.id.textView_product_list_price).text =
            if (product.price == 0F) "N/A" else product.price.toString()
        view.findViewById<TextView>(R.id.textView_product_list_amount).text =
            product.amount.toString()


        if (product.imageBase64 == null) {

            try {
                view.findViewById<ImageView>(R.id.imgView_product_list_img_product)
                    .setImageDrawable(GifDrawable(context?.resources!!, R.drawable.loading))
            } catch (ex: Exception) {

            }

        } else {
            view.findViewById<ImageView>(R.id.imgView_product_list_img_product)
                .setImageBitmap(product.imageBase64?.toBitmap())
        }


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
        amountView.setText(product.amount.toString())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        model = getViewModel()
        init()
        setObservers()

        val catalogId = arguments?.getInt("catalogId")
        if (catalogId != null)
            model.getProductsByCatalog(catalogId)
        else {
            //TODO show snackbar showing message error
        }
    }

    override fun setAdapterListeners(view: View, obj: Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_product_list_remove).setOnClickListener {

            if (product.amount > 0)
                product.amount--

            if (product.amount == 0)
                model.removeProductFromShop(product)

            adapter.notifyDataSetChanged()
            (context as IRepaintShopIcon).repaintShopIcon()

        }

        view.findViewById<Button>(R.id.button_product_list_add).setOnClickListener {

            product.amount++

            if (!model.existProduct(product.id))
                model.addProductToShop(product)

            adapter.notifyDataSetChanged()
            (context as IRepaintShopIcon).repaintShopIcon()

        }

        view.findViewById<ImageView>(R.id.imgView_product_list_heart).setOnClickListener {
            product.favorite = !product.favorite
            adapter.notifyDataSetChanged()

            model.writeProductsFavorites(
                context,
                products.filter { p -> p.favorite }.map { p -> p.id })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        var map = mutableMapOf<String, Int>()
        map["main_menu"] = R.menu.main_menu
        (context as ISetActionBar)?.setActionBar(map)
    }


    private fun setItemsSpinner() {

        lateinit var categories: MutableList<String>
        lateinit var order: MutableList<String>

        categories = mutableListOf()
        categories.add(this.resources.getString(R.string.product_categories))
        products.groupBy { p -> p.category }.keys.forEach { s -> categories.add(s) }

        var categoriesAdapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            categories
        )
        spinner_product_list_categories.adapter = categoriesAdapter

        spinner_product_list_categories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        adapter.products = products
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.products =
                            products.filter { p -> p.category === categories[position] }
                        adapter.notifyDataSetChanged()
                    }

                }

            }
        order = mutableListOf()
        order.add("A-Z")
        order.add("Z-A")
        order.add(this.resources.getString(R.string.product_order_by_price_low))
        order.add(this.resources.getString(R.string.product_order_by_price_high))

        var orderAdapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            order
        )
        spinner_product_list_order.adapter = orderAdapter
        spinner_product_list_order.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                    0 -> {
                        adapter.products =
                            products.sortedBy { p -> p.name }; adapter.notifyDataSetChanged()
                    }
                    1 -> {
                        adapter.products =
                            products.sortedByDescending { p -> p.name }; adapter.notifyDataSetChanged()
                    }
                    2 -> {
                        adapter.products =
                            products.sortedBy { p -> p.price }; adapter.notifyDataSetChanged()
                    }
                    3 -> {
                        adapter.products =
                            products.sortedByDescending { p -> p.price }; adapter.notifyDataSetChanged()
                    }
                }
            }

        }
    }

    private fun loadImages(items: List<UrlLoadedImage>) {

        model.loadImages(items)
            .observe(
                this.activity as LifecycleOwner,
                Observer<List<UrlLoadedImage>> { items ->

                    items.forEach { item ->

                        this.products.find { c -> c.id == item.id }?.imageBase64 =
                            item.imageBase64
                    }
                    adapter.notifyDataSetChanged()
                    refreshLayout.isRefreshing = false
                })
    }


    fun setObservers() {


        model.getProductsByCatalogObservable().observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Product>>> { it ->


                products = (it.serverData?.data ?: listOf())
                adapter.products = products
                setItemsSpinner()
                setProductCurrentState()
                adapter.notifyDataSetChanged()
                var items =
                    products.filter { p -> p.imageUrl != null && p.imageBase64 == null }.map { p ->

                        UrlLoadedImage(p.id, null, p.imageUrl!!)
                    }

                loadImages(items)

            })

        model.getErrorObservable()
            .observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })
    }

    private fun init() {

        var map = mutableMapOf<String, Int>()
        map["cart_menu"] = R.menu.cart_menu
        (context as ISetActionBar)?.setActionBar(map)

        var layout = LinearLayoutManager(this.context)
        adapter = ProductAdapter(
            listOf(),
            this
        )
        recyclerView = this.view!!.findViewById(R.id.recView_product_list_fragment)
        recyclerView.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()

        refreshLayout = this.view?.findViewById(R.id.swipeRefresh_products_fragment)!!
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        refreshLayout.setOnRefreshListener {

            model.getProductsByCatalog(arguments?.getInt("catalogId")!!)
        }


    }


    private fun setProductCurrentState() {

        var aux = mutableListOf<Product>()

        if (model.getProductsFromShop().isNotEmpty()) {

            val productsShop = model.getProductsFromShop()
            this.products.forEach { p ->

                val found = productsShop.firstOrNull { _p -> _p.id === p.id }
                if (found != null) {

                    p.amount = found.amount
                    p.favorite = found.favorite
                    aux.add(p)
                }

            }
            model.cleanProducts()
            model.setProductsToShop(aux)
        }

        val favorites = model.loadFavoritesProducts(context)
        this.products.forEach { p -> p.favorite = false }
        if (favorites != null)
            this.products.filter { p ->
                p.id in favorites
            }.forEach { p -> p.favorite = true }
    }
}
