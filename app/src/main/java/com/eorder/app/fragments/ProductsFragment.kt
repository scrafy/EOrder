package com.eorder.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eorder.app.R
import com.eorder.app.activities.CenterActivity
import com.eorder.app.com.eorder.app.adapters.fragments.ProductAdapter
import com.eorder.app.com.eorder.app.viewmodels.fragments.ProductsViewModel
import com.eorder.infrastructure.models.Product
import com.eorder.infrastructure.models.ServerResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ProductsFragment : Fragment() {

    var model: ProductsViewModel? = null
    var recyclerView : RecyclerView? = null
    var adapter = ProductAdapter(mutableListOf())

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel

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
            model?.getProductsByCatalog(catalogId)

        else{
            //TODO show snackbar showing message error
        }
    }

    fun setObservers(){

        model?.getProductsByCatalogObservable()?.observe((this.activity as CenterActivity), Observer<ServerResponse<List<Product>>> { it ->

            adapter.products = it.serverData?.data ?: mutableListOf()
            adapter.notifyDataSetChanged()
        })

        model?.getErrorObservable()?.observe((this.activity as CenterActivity), Observer<Throwable>{ ex ->

            model?.manageExceptionService?.manageException(this, ex)
        })
    }

    private fun init(){

        var layout = LinearLayoutManager(this.context)

        recyclerView = this.view?.findViewById<RecyclerView>(R.id.recView_product_list_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
