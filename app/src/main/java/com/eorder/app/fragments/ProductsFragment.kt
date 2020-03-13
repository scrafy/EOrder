package com.eorder.app.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.CenterActivity
import com.eorder.app.adapters.fragments.ProductAdapter
import com.eorder.app.com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.application.models.Product
import com.eorder.infrastructure.models.ServerResponse
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel



class ProductsFragment : Fragment() {

    lateinit var model: ProductsViewModel
    lateinit var recyclerView : RecyclerView
    lateinit var adapter: ProductAdapter
    lateinit var products:List<Product>

    companion object {
        fun newInstance() = ProductsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    private fun setItemsSpinner(){

        lateinit var categories: MutableList<String>
        lateinit var order: MutableList<String>

        categories = mutableListOf()
        categories.add(this.resources.getString(R.string.product_categories))
        products.groupBy { p -> p.category.name }.keys?.forEach { s -> categories.add(s) }

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

                    0 -> { adapter.products = products?.sortedBy { p -> p.name } ?: listOf(); adapter.notifyDataSetChanged() }
                    1 -> { adapter.products = products?.sortedByDescending { p -> p.name } ?: listOf(); adapter.notifyDataSetChanged() }
                    2 -> { adapter.products = products?.sortedBy { p -> p.price } ?: listOf(); adapter.notifyDataSetChanged() }
                    3 -> { adapter.products = products?.sortedByDescending { p -> p.price } ?: listOf(); adapter.notifyDataSetChanged() }
                }
            }

        }
    }

    fun setObservers(){

        model.getProductsByCatalogObservable().observe((this.activity as CenterActivity), Observer<ServerResponse<List<Product>>> { it ->

            products = it.serverData?.data ?: listOf()
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
        adapter = ProductAdapter(listOf())
        recyclerView = this.view!!.findViewById(R.id.recView_product_list_fragment)
        recyclerView.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

}
