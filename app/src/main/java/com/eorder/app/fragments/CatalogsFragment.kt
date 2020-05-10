package com.eorder.app.fragments


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.eorder.app.R
import com.eorder.app.adapters.fragments.CatalogsAdapter
import com.eorder.app.helpers.GridLayoutItemDecoration
import com.eorder.app.helpers.LoadImageHelper
import com.eorder.app.interfaces.IRepaintModel
import com.eorder.app.interfaces.ISelectCatalog
import com.eorder.app.interfaces.ISetAdapterListener
import com.eorder.app.viewmodels.fragments.CatalogsViewModel
import com.eorder.app.widgets.SnackBar
import com.eorder.application.factories.Gson
import com.eorder.application.interfaces.IShowSnackBarMessage
import com.eorder.domain.models.Catalog
import com.eorder.domain.models.ServerResponse
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.catalogs_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
class CatalogsFragment : BaseFragment(),
    IShowSnackBarMessage, IRepaintModel,
    ISetAdapterListener {


    private lateinit var model: CatalogsViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: CatalogsAdapter
    private lateinit var catalogs: List<Catalog>
    private lateinit var refreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.catalogs_fragment, container, false)

    }

    override fun setAdapterListeners(view: View, obj: Any?) {
        val catalog = (obj as Catalog)

        view.findViewById<CardView>(R.id.cardView_catalog_list_item).setOnClickListener { v ->

            (context as ISelectCatalog).selectCatalog(catalog)
        }
    }

    override fun repaintModel(view: View, model: Any?) {

        val catalog = (model as Catalog)

        view.findViewById<TextView>(R.id.textView_products_list_category).text = catalog.sellerName
        view.findViewById<TextView>(R.id.textView_catalogs_list_total_products).text =
            resources.getString(R.string.catalog_fragment_number_of_products)
                .format(catalog.totalProducts)

        try{
            Glide.with(context!!).load(catalog.imageUrl).into(view.findViewById<ImageView>(R.id.imgView_catalog_list_img))
        }catch (ex:Exception){
            LoadImageHelper().setGifLoading(view.findViewById<ImageView>(R.id.imgView_catalog_list_img))
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        init()
        setObservers()


    }

    override fun showMessage(message: String) {
        editText_activity_main_code_input.text.clear()
        SnackBar(
            context!!,
            swipeRefresh_catalogs_fragment,
            resources.getString(R.string.close),
            message
        ).show()
    }

    fun setObservers() {

        model.getCatalogBySellersObservable()
            .observe((context as LifecycleOwner), Observer<ServerResponse<List<Catalog>>> {


                catalogs = it.serverData?.data ?: mutableListOf()

                adapter.catalogs = catalogs
                adapter.notifyDataSetChanged()
                refreshLayout.isRefreshing = false

            })

        model.getErrorObservable()
            .observe(this.activity as LifecycleOwner, Observer<Throwable> { ex ->

                refreshLayout.isRefreshing = false
                model.getManagerExceptionService().manageException(this.context!!, ex)

            })


    }

    private fun init() {

        val layout = GridLayoutManager(context, 2)
        adapter = CatalogsAdapter(
            this,
            listOf()
        )

        recyclerView = this.view?.findViewById(R.id.recView_catalogs_fragment)
        recyclerView?.adapter = adapter
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = layout
        recyclerView?.itemAnimator = DefaultItemAnimator()

        refreshLayout = this.view?.findViewById(R.id.swipeRefresh_catalogs_fragment)!!
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        refreshLayout.setOnRefreshListener {

            model.getCatalogByCenter(arguments?.getInt("centerId")!!)
        }

        recyclerView?.addItemDecoration(
            GridLayoutItemDecoration(
                2,
                50,
                true
            )
        )
        catalogs = Gson.Create().fromJson(arguments?.getString("catalogs"), object: TypeToken<List<Catalog>>(){}.type)
        adapter.catalogs = catalogs
        adapter.notifyDataSetChanged()
    }

}
