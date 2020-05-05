package com.eorder.app.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.eorder.app.R
import com.eorder.app.com.eorder.app.adapters.fragments.CategoriesAdapter
import com.eorder.app.com.eorder.app.interfaces.ISelectCategory
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.CategoriesViewModel
import com.eorder.app.widgets.SnackBar
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerResponse
import kotlinx.android.synthetic.main.categories_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class CategoriesFragment : BaseFragment(), IRepaintModel, IShowSnackBarMessage,
    ISetAdapterListener {


    private lateinit var model: CategoriesViewModel
    private lateinit var categories: List<Category>
    private lateinit var adapter: CategoriesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        setObservers()
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

        view.findViewById<TextView>(R.id.textView_category_list_name)
            .setOnClickListener {

                (context as ISelectCategory).selectCategory(category)
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
                categories = it.serverData?.data ?: listOf()
                adapter = CategoriesAdapter(categories, this)
                listView_categories_fragment_listview.adapter = adapter
                adapter.notifyDataSetChanged()
            })

        model.getErrorObservable()
            ?.observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                //refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)
            })

    }

    private fun init() {
        model.getCategories(arguments!!.getInt("catalogId"))

    }

}

