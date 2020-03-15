package com.eorder.app.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.BaseActivity
import com.eorder.app.activities.CenterActivity
import com.eorder.app.adapters.fragments.ProductAdapter
import com.eorder.app.com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.com.eorder.app.interfaces.IToolBarSearch
import com.eorder.app.com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.application.models.Product
import com.eorder.infrastructure.models.ServerResponse
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ProductsFragment : Fragment(), IToolBarSearch, ISetAdapterListener {

    lateinit var model: ProductsViewModel
    lateinit var recyclerView : RecyclerView
    lateinit var adapter: ProductAdapter
    lateinit var products:List<Product>


    companion object {
        var self: ProductsFragment? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        self = this
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        var map = mutableMapOf<String, Int>()
        map["product_list_menu"] = R.menu.product_list_menu
        (this.activity as BaseActivity)?.addActionBar(map)


        model = getViewModel()
        init()
        setObservers()
        val catalogId = arguments?.getInt("catalogId")
        if ( catalogId != null)
            model.getProductsByCatalog(catalogId)

        else{
            //TODO show snackbar showing message error
        }
    }


    override fun setAdapterListeners(view: View, obj:Any?) {

        var product = obj as Product
        view.findViewById<Button>(R.id.button_product_list_remove).setOnClickListener { it ->

            if (product.amount > 0)
                product.amount--
            adapter.notifyDataSetChanged()

        }

        view.findViewById<Button>(R.id.button_product_list_add).setOnClickListener { it ->

            product.amount++
            adapter.notifyDataSetChanged()

        }

        view.findViewById<ImageView>(R.id.imgView_product_list_heart).setOnClickListener { it ->
            product.favorite = !product.favorite
            adapter.notifyDataSetChanged()

        }


    }


    override fun search(query: String) {
        adapter.products = products.filter { p -> p.name.toLowerCase().contains(query.toLowerCase()) }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy(){
        super.onDestroy()
        var map = mutableMapOf<String, Int>()
        map["main_menu"] = R.menu.main_menu
        (this.activity as BaseActivity)?.addActionBar(map)
    }


    private fun setItemsSpinner(){

        lateinit var categories: MutableList<String>
        lateinit var order: MutableList<String>

        categories = mutableListOf()
        categories.add(this.resources.getString(R.string.product_categories))
        products.groupBy { p -> p.category.name }.keys.forEach { s -> categories.add(s) }

        var categoriesAdapter = ArrayAdapter<String>(this.activity as Context, android.R.layout.simple_spinner_item,categories)
        spinner_product_list_categories.adapter = categoriesAdapter

        spinner_product_list_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0){
                    adapter.products = products
                    adapter.notifyDataSetChanged()
                }else{
                    adapter.products = products.filter { p -> p.category.name === categories[position] }
                    adapter.notifyDataSetChanged()
                }

            }

        }
        order = mutableListOf()
        order.add("A-Z")
        order.add("Z-A")
        order.add(this.resources.getString(R.string.product_order_by_price_low))
        order.add(this.resources.getString(R.string.product_order_by_price_high))

        var orderAdapter = ArrayAdapter<String>(this.activity as Context, android.R.layout.simple_spinner_item,order)
        spinner_product_list_order.adapter = orderAdapter
        spinner_product_list_order.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){

                    0 -> { adapter.products = products.sortedBy { p -> p.name } ; adapter.notifyDataSetChanged() }
                    1 -> { adapter.products = products.sortedByDescending { p -> p.name } ; adapter.notifyDataSetChanged() }
                    2 -> { adapter.products = products.sortedBy { p -> p.price } ; adapter.notifyDataSetChanged() }
                    3 -> { adapter.products = products.sortedByDescending { p -> p.price }; adapter.notifyDataSetChanged() }
                }
            }

        }
    }

    fun setObservers(){

        model.getProductsByCatalogObservable().observe((this.activity as CenterActivity), Observer<ServerResponse<List<Product>>> { it ->

            products = (it.serverData?.data ?: listOf())
            adapter.products = products
            adapter.notifyDataSetChanged()
            setItemsSpinner()

        })

        model.getErrorObservable().observe((this.activity as CenterActivity), Observer<Throwable>{ ex ->

            model.manageExceptionService.manageException(this, ex)
        })
    }

    private fun init(){

        var layout = LinearLayoutManager(this.context)
        adapter = ProductAdapter(listOf(), this)
        recyclerView = this.view!!.findViewById(R.id.recView_product_list_fragment)
        recyclerView.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

}
