package com.pedidoe.app.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pedidoe.app.R
import com.pedidoe.app.com.eorder.app.adapters.fragments.CategoriesAdapter
import com.pedidoe.app.com.eorder.app.interfaces.ISelectCategory
import com.pedidoe.app.interfaces.IRepaintModel
import com.pedidoe.app.interfaces.ISetAdapterListener
import com.pedidoe.app.viewmodels.fragments.CategoriesViewModel
import com.pedidoe.app.widgets.SnackBar
import com.pedidoe.domain.factories.Gson
import com.pedidoe.application.interfaces.IShowSnackBarMessage
import com.pedidoe.domain.models.*
import kotlinx.android.synthetic.main.categories_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class CategoriesFragment : BaseFragment(), IRepaintModel, IShowSnackBarMessage,
    ISetAdapterListener {


    private lateinit var model: CategoriesViewModel
    private var categories: List<Category> = listOf()
    private lateinit var adapter: CategoriesAdapter
    private lateinit var catalog: Catalog
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var categorySelected: Category


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        setObservers()
        if ( categories.isNullOrEmpty() )
            init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.categories_fragment, container, false)

    }


    override fun setAdapterListeners(view: View, obj: Any?) {

        val category = (obj as Category)

        view.findViewById<LinearLayout>(R.id.linearLayout_category_list_container)
            .setOnClickListener {
                categorySelected = category

                var dataString = Gson.Create().toJson(
                    DataProductFragment(
                        categories,
                        categorySelected,
                        catalog.id,
                        model.getCenterSelected()!!

                    )
                )
                (this.activity as ISelectCategory).selectCategory(dataString)
            }
    }

    override fun showMessage(message: String) {
        SnackBar(
            context!!,
            swipeRefresh_categories_fragment,
            resources.getString(R.string.close),
            message
        ).show()
    }

    override fun repaintModel(view: View, model: Any?) {

        val category = (model as Category)
        view.findViewById<TextView>(R.id.textView_category_list_name).text =
            category.categoryName

        view.findViewById<TextView>(R.id.textView_category_list_count_products).text =
            category.totalProducts.toString()
    }


    fun setObservers() {

        model.categoriesResult.observe(
            this.activity as LifecycleOwner,
            Observer<ServerResponse<List<Category>>> {
                refreshLayout.isRefreshing = false
                categories = it.ServerData?.Data ?: listOf()
                adapter = CategoriesAdapter(categories, this)
                listView_categories_fragment_listview.adapter = adapter
                adapter.notifyDataSetChanged()
                imgView_products_fragment_pedidoe_loading.visibility = View.INVISIBLE
            })

        model.getErrorObservable()
            .observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })

    }

    private fun init() {
        this.catalog = Gson.Create().fromJson(arguments!!.getString("catalog"), Catalog::class.java)
        model.getCategories(this.catalog.id)
        imgView_products_fragment_pedidoe_loading.visibility = View.VISIBLE
        refreshLayout = this.view?.findViewById(R.id.swipeRefresh_categories_fragment)!!
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        refreshLayout.setOnRefreshListener {

            model.getCategories(this.catalog.id)
        }

    }

}

